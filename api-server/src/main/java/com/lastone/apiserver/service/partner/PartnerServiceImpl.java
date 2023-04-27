package com.lastone.apiserver.service.partner;

import com.lastone.apiserver.exception.partner.TodayPartnerNotFoundException;
import com.lastone.core.domain.application.Application;
import com.lastone.core.domain.application.ApplicationStatus;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.dto.partner.TodayPartnerDto;
import com.lastone.core.repository.application.ApplicationRepository;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final RecruitmentRepository recruitmentRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public TodayPartnerDto findTodayPartner(Long memberId) {

        // 모집글에 매칭된 파트너 먼저 조회
        Recruitment recruitment = recruitmentRepository.findOneCompleteStatusAtTodayByMemberId(memberId);
        if (recruitment != null) {
            Member partner = findSuccessApplicant(recruitment.getApplications());
            if (partner != null) {
                return TodayPartnerDto.builder()
                        .recruitmentId(recruitment.getId())
                        .partnerName(partner.getNickname())
                        .workoutPart(recruitment.getWorkoutPart())
                        .startedAt(recruitment.getStartedAt())
                        .gym(recruitment.getGym().getName())
                        .build();
            }
        }

        // 모집글에 매칭된 파트너가 없으면 요청한 신청이 Success 모집글 작성자 정보 조회
        Application application = applicationRepository.findMyTodaySuccessApplication(memberId);
        if (application != null) {
            return TodayPartnerDto.builder()
                    .recruitmentId(application.getRecruitment().getId())
                    .partnerName(application.getRecruitment().getMember().getNickname())
                    .workoutPart(application.getRecruitment().getWorkoutPart())
                    .startedAt(application.getRecruitment().getStartedAt())
                    .gym(application.getRecruitment().getGym().getName())
                    .build();
        }

        // 요청 받은 신청에도 매칭 내역이 없고, 요청 한 신청에도 매칭 내역이 없으면 예외처리
        throw new TodayPartnerNotFoundException();
    }

    private Member findSuccessApplicant(List<Application> applications) {
        return applications.stream()
                .filter(application -> application.getStatus().equals(ApplicationStatus.SUCCESS))
                .map(Application::getApplicant)
                .findFirst()
                .orElse(null);
    }
}