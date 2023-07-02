package com.lastone.apiserver.service.recruitment;

import com.lastone.apiserver.constant.ImgTypes;
import com.lastone.apiserver.exception.mypage.MemberNotFountException;
import com.lastone.apiserver.exception.recruitment.IncorrectWriterException;
import com.lastone.apiserver.exception.recruitment.RecruitmentStatusException;
import com.lastone.apiserver.exception.recruitment.RecruitmentImgCountException;
import com.lastone.apiserver.exception.recruitment.RecruitmentNotFoundException;
import com.lastone.apiserver.service.s3.S3Service;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.domain.recruitment_img.RecruitmentImg;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.recruitment.*;
import com.lastone.core.util.mapper.GymMapper;
import com.lastone.core.repository.gym.GymRepository;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import com.lastone.core.repository.recruitment_img.RecruitmentImgRepository;
import com.lastone.core.repository.sbd.SbdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentServiceImpl implements RecruitmentService {

    private static final int IMG_MAX_COUNT = 3;

    private final S3Service s3Service;

    private final RecruitmentRepository recruitmentRepository;

    private final RecruitmentImgRepository recruitmentImgRepository;

    private final GymRepository gymRepository;

    private final MemberRepository memberRepository;

    private final SbdRepository sbdRepository;

    private final GymMapper gymMapper;

    @Override
    public Slice<RecruitmentListDto> getList(RecruitmentSearchCondition searchCondition) {
        return recruitmentRepository.getListDto(searchCondition);
    }

    @Override
    public RecruitmentDetailDto getDetail(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.getDetail(recruitmentId).orElseThrow(RecruitmentNotFoundException::new);
        RecruitmentDetailDto recruitmentDetailDto = RecruitmentDetailDto.toDto(recruitment);
        recruitmentDetailDto.setSbdDto(sbdRepository.findLatestRecordByMemberId(recruitmentDetailDto.getMemberId()));
        return recruitmentDetailDto;
    }

    @Override
    public List<RecruitmentListDto> getMainList() {
        return recruitmentRepository.getListDtoInMainPage();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createRecruitment(Long memberId, RecruitmentRequestDto recruitmentRequestDto, List<MultipartFile> imgFiles) throws IOException {
        Gym gym = findGym(recruitmentRequestDto.getGym());
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFountException::new);
        Recruitment recruitment = Recruitment.create(member, gym, recruitmentRequestDto);

        if (imgFileIsExist(imgFiles)) {
            recruitment.setImgFiles(saveRecruitmentImg(imgFiles));
        }
        Recruitment saveRecruitment = recruitmentRepository.save(recruitment);
        return saveRecruitment.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRecruitment(Long recruitmentId, Long memberId, RecruitmentRequestDto recruitmentRequestDto, List<MultipartFile> imgFiles) throws IOException {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(RecruitmentNotFoundException::new);
        validateWriterAndMember(recruitment.getMember().getId(), memberId);
        /* 모집글 수정 작업 */
        recruitment.update(recruitmentRequestDto);
        updateGym(recruitment, recruitmentRequestDto.getGym());
        updateImgFiles(recruitment, imgFiles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRecruitment(Long recruitmentId, Long memberId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(RecruitmentNotFoundException::new);
        if (recruitment.isDeleted()) {
            throw new RecruitmentStatusException(ErrorCode.RECRUITMENT_ALREADY_DELETE);
        }
        validateWriterAndMember(recruitment.getMember().getId(), memberId);
        deleteImgFile(recruitment.getRecruitmentImgs());
        recruitment.delete();
    }

    @Override
    public RecruitmentApplyStatusForMember isAlreadyApplyRecruitment(Long recruitmentId, Long memberId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(RecruitmentNotFoundException::new);
        boolean result = recruitment.getApplications().stream()
                .anyMatch(application -> application.getApplicant().getId().equals(memberId));
        return new RecruitmentApplyStatusForMember(result);
    }

    private void updateImgFiles(Recruitment recruitment, List<MultipartFile> imgFiles) throws IOException {
        if (imgFileIsExist(imgFiles)) {
            return;
        }
        deleteImgFile(recruitment.getRecruitmentImgs());
        List<RecruitmentImg> recruitmentImgList = saveRecruitmentImg(imgFiles);
        recruitment.updateImg(recruitmentImgList);
    }

    private void deleteImgFile(List<RecruitmentImg> recruitmentImgList) {
        recruitmentImgList.forEach(recruitmentImg -> s3Service.delete(recruitmentImg.getImgUrl()));
    }

    private boolean imgFileIsExist(List<MultipartFile> imgFiles) {
        if (ObjectUtils.isEmpty(imgFiles)) {
            return false;
        }
        /* 각각의 이미지 파일 형식 검증 */
        imgFiles.forEach(imgFile -> ImgTypes.isSupport(Objects.requireNonNull(imgFile.getOriginalFilename())));
        return true;
    }

    private void updateGym(Recruitment recruitment, GymDto gymDto) {
        Gym gym = findGym(gymDto);
        if (!recruitment.getGym().equals(gym)) {
            recruitment.updateGym(gym);
        }
    }

    private void validateWriterAndMember(Long writerId, Long memberId) {
        if (!writerId.equals(memberId)) {
            throw new IncorrectWriterException();
        }
    }

    private List<RecruitmentImg> saveRecruitmentImg(List<MultipartFile> imgFiles) throws IOException {
        validateImgCount(imgFiles);
        List<String> imgUrls = s3Service.upload(imgFiles);
        return imgUrls.stream()
                .map(imgUrl -> recruitmentImgRepository.save(new RecruitmentImg(imgUrl)))
                .collect(Collectors.toList());
    }

    private Gym findGym(GymDto gymdto) {
        Gym gym = gymRepository.findByGymDto(gymdto);
        return Objects.requireNonNullElseGet(gym, () -> gymRepository.save(gymMapper.toEntity(gymdto)));
    }

    private void validateImgCount(List<MultipartFile> imgFiles) {
        if (imgFiles.size() > IMG_MAX_COUNT) {
            throw new RecruitmentImgCountException();
        }
    }
}
