package com.lastone.apiserver.service.application;

import com.lastone.apiserver.exception.mypage.MemberNotFountException;
import com.lastone.apiserver.exception.recruitment.RecruitmentNotFoundException;
import com.lastone.core.domain.application.Application;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.repository.application.ApplicationRepository;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
