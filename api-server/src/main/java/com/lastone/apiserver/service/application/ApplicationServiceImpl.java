package com.lastone.apiserver.service.application;

import com.lastone.apiserver.exception.application.*;
import com.lastone.apiserver.exception.mypage.MemberNotFountException;
import com.lastone.apiserver.exception.recruitment.RecruitmentNotFoundException;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.domain.application.Application;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.notification.Notification;
import com.lastone.core.domain.notification.NotificationType;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.dto.applicaation.ApplicationReceivedDto;
import com.lastone.core.dto.applicaation.ApplicationRequestedDto;
import com.lastone.core.repository.application.ApplicationRepository;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.repository.notification.NotificationRepository;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final MemberRepository memberRepository;

    private final RecruitmentRepository recruitmentRepository;

    private final NotificationRepository notificationRepository;

    @Override
    public void createApplication(Long applicantId, Long recruitmentId) {
        Member member = memberRepository.findById(applicantId).orElseThrow(MemberNotFountException::new);
        Recruitment recruitment = recruitmentRepository
                .findByIdAndDeletedIsFalse(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);
        validateApplication(member, recruitment);
        applicationRepository.save(new Application(recruitment, member));

        notificationRepository.save(
                Notification.builder()
                        .recruitment(recruitment)
                        .member(recruitment.getMember())
                        .senderNickname(member.getNickname())
                        .notificationType(NotificationType.MATCHING_REQUEST)
                        .build());
    }

    private void validateApplication(Member member, Recruitment recruitment) {

        if (isSameUser(member, recruitment.getMember())) {
            throw new ApplicantIsEqualToWriterException();
        }
        if (isRecruitmentClosed(recruitment)) {
            throw new ApplyToIncorrectRecruitmentException(ErrorCode.APPLY_TO_CLOSED_RECRUITMENT);
        }
        if (isRecruitmentExpiration(recruitment)) {
            throw new ApplyToIncorrectRecruitmentException(ErrorCode.APPLY_TO_EXPIRATION_RECRUITMENT);
        }
        if (isRecruitmentAlreadyApplied(member, recruitment)) {
            throw new AlreadyAppliedException();
        }
    }

    private boolean isSameUser(Member applicant, Member writer) {
        return applicant.getId().equals(writer.getId());
    }

    private boolean isRecruitmentExpiration(Recruitment recruitment) {
        LocalDateTime startedAt = recruitment.getStartedAt();
        LocalDateTime now = LocalDateTime.now();
        return startedAt.isBefore(now);
    }

    private boolean isRecruitmentClosed(Recruitment recruitment) {
        RecruitmentStatus recruitmentStatus = recruitment.getRecruitmentStatus();
        if (recruitmentStatus.equals(RecruitmentStatus.RECRUITING)) {
            return false;
        }
        return true;
    }

    private boolean isRecruitmentAlreadyApplied(Member member, Recruitment recruitment) {
        List<Application> applications = applicationRepository.findAllByRecruitmentId(recruitment.getId());
        List<Long> applicantIdList = applications.stream()
                .map(application -> application.getApplicant().getId()).collect(Collectors.toList());
        if (applicantIdList.contains(member.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public List<ApplicationReceivedDto> getReceivedListByMemberId(Long memberId) {
        return applicationRepository.getReceivedList(memberId);
    }

    @Override
    public List<ApplicationRequestedDto> getRequestedListByMemberId(Long memberId) {
        return applicationRepository.getRequestedList(memberId);
    }

    @Override
    public void cancel(Long applicationId, Long requestMemberId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(ApplicationNotFoundException::new);
        validateApplicationCancelable(application, requestMemberId);
        application.cancel();
    }

    private void validateApplicationCancelable(Application application, Long requestMemberId) {
        if (isDifferent(application.getApplicant().getId(), requestMemberId)) {
            throw new ApplicationNotEqualRequestIdException();
        }
        if (isRecruitmentClosed(application.getRecruitment())) {
            throw new AlreadyMatchingCompleteException();
        }
    }

    private boolean isDifferent(Long applicantId, Long requestMemberId) {
        return !applicantId.equals(requestMemberId);
    }
}
