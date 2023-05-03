package com.lastone.apiserver.service.matching;

import com.lastone.apiserver.exception.application.ApplicationNotFoundException;
import com.lastone.apiserver.exception.application.ApplicationStatusInCorrectException;
import com.lastone.apiserver.exception.recruitment.IncorrectWriterException;
import com.lastone.apiserver.exception.recruitment.RecruitmentNotFoundException;
import com.lastone.apiserver.exception.recruitment.RecruitmentNotIncludeApplicationException;
import com.lastone.apiserver.exception.recruitment.RecruitmentStatusException;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.domain.application.Application;
import com.lastone.core.domain.application.ApplicationStatus;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.repository.application.ApplicationRepository;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService{

    private final RecruitmentRepository recruitmentRepository;
    private final ApplicationRepository applicationRepository;
    private static final int RECRUITMENT_UPDATED_COUNT = 1;

    @Override
    public void completeMatching(Long recruitmentId, Long applicationId, Long requesterId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(RecruitmentNotFoundException::new);
        validateRequesterAuthorization(recruitment.getMember().getId(), requesterId);
        validateApplicationId(recruitment.getApplications(), applicationId);
        validateRecruitmentStatus(recruitment);

        int updateCount = recruitmentRepository.updateStatusToComplete(recruitmentId);
        if (updateCount != RECRUITMENT_UPDATED_COUNT) {
            throw new RecruitmentNotFoundException();
        }
        applicationRepository.updateStatus(recruitmentId, applicationId);
    }

    @Override
    public void cancelMatching(Long recruitmentId, Long applicationId, Long requesterId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(RecruitmentNotFoundException::new);
        validateRequesterAuthorization(recruitment.getMember().getId(), requesterId);
        validateApplicationId(recruitment.getApplications(), applicationId);
        recruitment.cancelMatching();
        Application application = applicationRepository.findById(applicationId).orElseThrow(ApplicationNotFoundException::new);
        validateApplicationStatus(application);
        application.fail();
    }

    private void validateRecruitmentStatus(Recruitment recruitment) {
        if (recruitment.getRecruitmentStatus().equals(RecruitmentStatus.RECRUITING)){
            return;
        }
        throw new RecruitmentStatusException(ErrorCode.IMPOSSIBLE_TO_MATCH_STATUS);
    }

    /* 해당 모집글에 속하는 신청 Id가 아닐 경우 예외 처리 */
    private void validateApplicationId(List<Application> applications, Long applicationId) {
        List<Long> applicationIds = applications.stream().map(Application::getId).collect(Collectors.toList());
        if (!applicationIds.contains(applicationId)) {
            throw new RecruitmentNotIncludeApplicationException();
        }
    }

    private void validateApplicationStatus(Application application) {
        if (application.getStatus() != ApplicationStatus.SUCCESS) {
            throw new ApplicationStatusInCorrectException(ErrorCode.APPLICATION_STATUS_INCORRECT);
        }
    }

    private void validateRequesterAuthorization(Long writerId, Long requesterId) {
        if (!requesterId.equals(writerId)) {
            throw new IncorrectWriterException();
        }
    }
}