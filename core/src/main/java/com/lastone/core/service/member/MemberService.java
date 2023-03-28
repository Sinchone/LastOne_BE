package com.lastone.core.service.member;

import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.member.MemberUpdateDto;
import com.lastone.core.mapper.mapper.MemberMapper;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    private final S3Service s3Service;


    public MemberDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NullPointerException::new);
        return memberMapper.toDto(member);
    }

    public void update(Long memberId, MemberUpdateDto memberUpdateDto, MultipartFile profileImg) throws IOException {
        Member member = memberRepository.findById(memberId).orElseThrow(NullPointerException::new);
        isDuplicatedNickname(memberUpdateDto.getNickname(), member.getNickname());

        if (profileImg != null) {
            s3Service.delete(member.getProfileUrl());
            memberUpdateDto.setProfileUrl(s3Service.upload(profileImg));
        }
        member.update(memberUpdateDto);
    }

    private void isDuplicatedNickname(String updateNickname, String nickname) {
        if (updateNickname.equals(nickname)) {
            return;
        }
        Optional<Member> findMember = memberRepository.findByNickname(updateNickname);
        if (findMember.isPresent()) {
            throw new IllegalArgumentException("이미 해당 닉네임을 지닌 회원이 존재합니다.");
        }
    }

    public void isExist(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new NullPointerException();
        }
    }
}
