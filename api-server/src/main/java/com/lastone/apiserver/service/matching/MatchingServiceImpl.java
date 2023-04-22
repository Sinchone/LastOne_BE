package com.lastone.apiserver.service.matching;

import com.lastone.apiserver.exception.recruitment.RecruitmentNotFoundException;
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
}