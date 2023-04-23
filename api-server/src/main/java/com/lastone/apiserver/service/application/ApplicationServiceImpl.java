package com.lastone.apiserver.service.application;

import com.lastone.apiserver.exception.application.ApplicationNotEqualRequestIdException;
import com.lastone.apiserver.exception.application.ApplicationNotFoundException;
import com.lastone.apiserver.exception.mypage.MemberNotFountException;
import com.lastone.apiserver.exception.recruitment.RecruitmentNotFoundException;
import com.lastone.core.domain.application.Application;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.dto.applicaation.ApplicationReceivedDto;
import com.lastone.core.dto.applicaation.ApplicationRequestedDto;
import com.lastone.core.repository.application.ApplicationRepository;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Override
    public void createApplication(Long applicantId, Long recruitmentId) {
        Member member = memberRepository.findById(applicantId).orElseThrow(MemberNotFountException::new);
        Recruitment recruitment = recruitmentRepository
                .findByIdAndDeletedIsFalse(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);

        Application application = new Application(recruitment, member);
        applicationRepository.save(application);
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
        validateRequestMember(application.getApplicant().getId(), requestMemberId);
        application.cancel();
    }

    private void validateRequestMember(Long applicantId, Long requestMemberId) {
        if (!applicantId.equals(requestMemberId)) {
            throw new ApplicationNotEqualRequestIdException();
        }
    }
}
