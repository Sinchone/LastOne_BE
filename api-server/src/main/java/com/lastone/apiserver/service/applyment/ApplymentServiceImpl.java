package com.lastone.apiserver.service.applyment;

import com.lastone.apiserver.exception.mypage.MemberNotFountException;
import com.lastone.apiserver.exception.recruitment.RecruitmentNotFoundException;
import com.lastone.core.domain.applyment.Applyment;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.repository.applyment.ApplymentRepository;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplymentServiceImpl implements ApplymentService{

    private final ApplymentRepository applymentRepository;
    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Override
    public void createApplyment(Long applyerId, Long recruitmentId) {
        Member member = memberRepository.findById(applyerId).orElseThrow(MemberNotFountException::new);
        Recruitment recruitment = recruitmentRepository
                .findByIdAndDeletedIsFalse(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);

        Applyment applyment = new Applyment(recruitment, member);
        applymentRepository.save(applyment);
    }
}
