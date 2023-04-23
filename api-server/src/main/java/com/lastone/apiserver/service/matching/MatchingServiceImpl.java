package com.lastone.apiserver.service.matching;

import com.lastone.apiserver.exception.application.ApplicationNotFoundException;
import com.lastone.apiserver.exception.application.ApplicationStatusInCorrectException;
import com.lastone.apiserver.exception.recruitment.IncorrectWriterException;
import com.lastone.apiserver.exception.recruitment.RecruitmentNotFoundException;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.domain.application.Application;
import com.lastone.core.domain.application.ApplicationStatus;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.repository.application.ApplicationRepository;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService{

    private final RecruitmentRepository recruitmentRepository;
    private final ApplicationRepository applicationRepository;
    private static final int RECRUITMENT_UPDATED_COUNT = 1;

    @Override
    public void completeMatching(Long recruitmentId, Long applicationId) {
        int updateCount = recruitmentRepository.updateStatusToComplete(recruitmentId);
        if (updateCount != RECRUITMENT_UPDATED_COUNT) {
            throw new RecruitmentNotFoundException();
        }
        applicationRepository.updateStatus(recruitmentId, applicationId);
    }

    @Override
    public void cancelMatching(Long recruitmentId, Long applicationId, Long requestMemberId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(RecruitmentNotFoundException::new);
        validateDeleteAuthorization(recruitment.getMember().getId(), requestMemberId);
        recruitment.cancelMatching();
        Application application = applicationRepository.findById(applicationId).orElseThrow(ApplicationNotFoundException::new);
        validateApplicationStatus(application);
        application.fail();
    }

    private void validateApplicationStatus(Application application) {
        if (application.getStatus() != ApplicationStatus.SUCCESS) {
            throw new ApplicationStatusInCorrectException(ErrorCode.APPLICATION_STATUS_INCORRECT);
        }
    }

    private void validateDeleteAuthorization(Long writerId, Long requestMemberId) {
        if (!requestMemberId.equals(writerId)) {
            throw new IncorrectWriterException();
        }
    }
}