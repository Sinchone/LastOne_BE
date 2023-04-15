package com.lastone.apiserver.service.recruitment;

import com.lastone.apiserver.exception.MemberNotFountException;
import com.lastone.apiserver.service.s3.S3Service;
import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.domain.recruitment_img.RecruitmentImg;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.recruitment.RecruitmentCreateDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import com.lastone.core.dto.recruitment.StartedAtDto;
import com.lastone.core.exception.ErrorCode;
import com.lastone.core.mapper.mapper.GymMapper;
import com.lastone.core.repository.gym.GymRepository;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import com.lastone.core.repository.recruitment_img.RecruitmentImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    private final GymMapper gymMapper;

    @Override
    public void createRecruitment(Long memberId, RecruitmentCreateDto recruitmentCreateDto, List<MultipartFile> imgFiles) throws IOException {
        Gym gym = findGym(recruitmentCreateDto.getGym());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFountException(ErrorCode.MEMBER_NOT_FOUND));
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
        if (imgFiles.isEmpty()) {
            recruitment.setImgFiles(saveRecruitmentImg(imgFiles));
        }
        recruitmentRepository.save(recruitment);
    }

    @Override
    public Page<RecruitmentListDto> getList(RecruitmentSearchCondition searchCondition) {
        Pageable pageable = PageRequest.of(searchCondition.getOffset(), searchCondition.getLimit());
        return recruitmentRepository.getListDto(pageable, searchCondition);
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
        StringBuffer sb = new StringBuffer();
        String time = sb.append(startedAt.getDate())
                .append(" ")
                .append(startedAt.getMeridiem().getText())
                .append(" ")
                .append(startedAt.getTime())
                .toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd a h:mm", Locale.KOREAN);
        return LocalDateTime.parse(time, formatter);
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
            throw new IllegalArgumentException("이미지 갯수는 최대 3개까지 등록 가능합니다.");// TODO 커스텀 예외처리
        }
    }
}
