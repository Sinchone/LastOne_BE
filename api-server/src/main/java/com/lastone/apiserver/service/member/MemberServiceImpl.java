package com.lastone.apiserver.service.member;

import com.lastone.apiserver.exception.MemberAlreadyExistException;
import com.lastone.apiserver.exception.MemberNotFountException;
import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.member.MemberUpdateDto;
import com.lastone.core.exception.ErrorCode;
import com.lastone.core.mapper.mapper.MemberMapper;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.apiserver.service.s3.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final S3ServiceImpl s3Service;


    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFountException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Member member, MemberUpdateDto memberUpdateDto, MultipartFile profileImg) throws IOException {
        isDuplicatedNickname(memberUpdateDto.getNickname(), member.getNickname());

        if (profileImg != null) {
            if (member.getProfileUrl() != null) {
                s3Service.delete(member.getProfileUrl());
            }
            memberUpdateDto.setProfileUrl(s3Service.upload(profileImg));
        }
        member.update(memberUpdateDto);
    }

    private void isDuplicatedNickname(String updateNickname, String nickname) {
        if (updateNickname.equals(nickname)) {
            return;
        }
        Optional<com.lastone.core.domain.member.Member> findMember = memberRepository.findByNickname(updateNickname);
        if (findMember.isPresent()) {
            throw new MemberAlreadyExistException(ErrorCode.MEMBER_ALREADY_EXIST);
        }
    }
}
