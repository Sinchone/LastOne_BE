package com.lastone.apiserver.service.recruitment;

import com.lastone.apiserver.exception.mypage.MemberNotFountException;
import com.lastone.apiserver.exception.recruitment.IncorrectWriterException;
import com.lastone.apiserver.exception.recruitment.RecruitmentImgCountException;
import com.lastone.apiserver.exception.recruitment.RecruitmentNotFoundException;
import com.lastone.apiserver.service.s3.S3Service;
import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.domain.recruitment_img.RecruitmentImg;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.recruitment.RecruitmentDetailDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentRequestDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import com.lastone.core.dto.recruitment.StartedAtDto;
import com.lastone.core.mapper.mapper.GymMapper;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Slf4j
@Service
@Transactional
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
        RecruitmentDetailDto recruitmentDetailDto = recruitmentRepository.getDetailDto(recruitmentId);
        if (recruitmentDetailDto == null) {
            throw new RecruitmentNotFoundException();
        }
        recruitmentDetailDto.setSbdDto(sbdRepository.findLatestRecordByMemberId(recruitmentDetailDto.getMemberId()));
        return recruitmentDetailDto;
    }

    @Override
    public List<RecruitmentListDto> getMainList() {
        return recruitmentRepository.getListDtoInMainPage();
    }

    @Override
    public void createRecruitment(Long memberId, RecruitmentRequestDto recruitmentCreateDto, List<MultipartFile> imgFiles) throws IOException {

        Gym gym = findGym(recruitmentCreateDto.getGym());

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFountException::new);

        Recruitment recruitment = Recruitment.builder()
                .member(member)
                .gym(gym)
                .title(recruitmentCreateDto.getTitle())
                .workoutPart(recruitmentCreateDto.getWorkoutPart())
                .description(recruitmentCreateDto.getDescription())
                .startedAt(startedAtToLocalDateTime(recruitmentCreateDto.getStartedAt()))
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .preferGender(recruitmentCreateDto.getPreferGender())
                .build();

        // List에 파일이 있지만 실제로 비어있는 경우 때문에 로직 수정
        if (imgFileIsNotEmpty(imgFiles)) {
            recruitment.setImgFiles(saveRecruitmentImg(imgFiles));
        }
        recruitmentRepository.save(recruitment);
    }

    @Override
    public void updateRecruitment(Long recruitmentId, Long memberId, RecruitmentRequestDto recruitmentUpdateDto, List<MultipartFile> imgFiles) throws IOException {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(RecruitmentNotFoundException::new);
        validateWriterAndMember(recruitment.getMember().getId(), memberId);
        recruitment.update(recruitmentUpdateDto);
        updateGym(recruitment, recruitmentUpdateDto.getGym());
        updateStartedAt(recruitment, recruitmentUpdateDto.getStartedAt());
        if (imgFileIsNotEmpty(imgFiles)) {
            updateImgFiles(recruitment, imgFiles);
        }

    }

    @Override
    public void deleteRecruitment(Long recruitmentId, Long memberId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(RecruitmentNotFoundException::new);
        validateWriterAndMember(recruitment.getMember().getId(), memberId);
        deleteImgFile(recruitment.getRecruitmentImgs());
        recruitment.delete();
    }

    private void updateImgFiles(Recruitment recruitment, List<MultipartFile> imgFiles) throws IOException {
        deleteImgFile(recruitment.getRecruitmentImgs());
        List<RecruitmentImg> recruitmentImgs = saveRecruitmentImg(imgFiles);
        recruitment.updateImg(recruitmentImgs);
    }

    private void deleteImgFile(List<RecruitmentImg> recruitmentImgs) {
        for (RecruitmentImg recruitmentImg : recruitmentImgs) {
            s3Service.delete(recruitmentImg.getImgUrl());
        }
    }

    private boolean imgFileIsNotEmpty(List<MultipartFile> imgFiles) {
        if (ObjectUtils.isEmpty(imgFiles)) {
            return false;
        }
        for (MultipartFile imgFile : imgFiles) {
            if (!ObjectUtils.isEmpty(imgFile)) {
                return true;
            }
        }
        return false;
    }

    private void updateStartedAt(Recruitment recruitment, StartedAtDto startedAt) {
        recruitment.updateStartedAt(startedAtToLocalDateTime(startedAt));
    }

    private void updateGym(Recruitment recruitment, GymDto gymDto) {
        Gym gym = findGym(gymDto);
        if (recruitment.getGym().equals(gym)) {
            return;
        }
        recruitment.updateGym(gym);
    }

    private void validateWriterAndMember(Long writerId, Long memberId) {
        if (writerId.equals(memberId)) {
            return;
        }
        throw new IncorrectWriterException();

    }

    private List<RecruitmentImg> saveRecruitmentImg(List<MultipartFile> imgFiles) throws IOException {
        validateImgCount(imgFiles);
        List<String> imgUrls = s3Service.upload(imgFiles);
        List<RecruitmentImg> recruitmentImgs = new ArrayList<>();
        for (String imgUrl : imgUrls) {
            recruitmentImgs.add(recruitmentImgRepository.save(new RecruitmentImg(imgUrl)));
        }
        return recruitmentImgs;
    }

    private LocalDateTime startedAtToLocalDateTime(StartedAtDto startedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd a h:mm", Locale.KOREAN);
        return LocalDateTime.parse(startedAt.toTime(), formatter);
    }

    private Gym findGym(GymDto gymdto) {
        Gym gym = gymRepository.findByGymDto(gymdto);
        if (gym != null) {
            return gym;
        }
        return gymRepository.save(gymMapper.toEntity(gymdto)); // 없으면 헬스장 새로 생성 후 반환
    }

    private void validateImgCount(List<MultipartFile> imgFiles) {
        if (imgFiles.size() > IMG_MAX_COUNT) {
            throw new RecruitmentImgCountException();
        }
    }
}
