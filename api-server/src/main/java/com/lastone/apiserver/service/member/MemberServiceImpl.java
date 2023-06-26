package com.lastone.apiserver.service.member;

import com.lastone.apiserver.exception.mypage.MemberAlreadyExistException;
import com.lastone.apiserver.exception.mypage.MemberNotFountException;
import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.member.MemberUpdateDto;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.apiserver.service.s3.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
        return memberRepository.findById(memberId).orElseThrow(MemberNotFountException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Member member, MemberUpdateDto memberUpdateDto, MultipartFile profileImg) throws IOException {
        validateNickname(member.getNickname(), memberUpdateDto.getNickname());
        setProfileImg(member, memberUpdateDto, profileImg);
        member.update(memberUpdateDto);
    }

    private void setProfileImg(Member member, MemberUpdateDto memberUpdateDto, MultipartFile profileImg) throws IOException {
        if (profileImg == null) {
            return;
        }
        if (StringUtils.hasText(member.getProfileUrl())) {
            s3Service.delete(member.getProfileUrl());
        }
        String profileUrl = s3Service.upload(profileImg);
        memberUpdateDto.setProfileUrl(profileUrl);
    }

    private void validateNickname(String nickname, String updateNickname) {
        if (isEqualToPrevious(nickname, updateNickname)) {
            return;
        }
        if (isDuplicateNickname(updateNickname)) {
            throw new MemberAlreadyExistException();
        }
    }

    private boolean isEqualToPrevious(String nickname, String updateNickname) {
        return nickname.equals(updateNickname);
    }

    public boolean isDuplicateNickname(String updateNickname) {
        Optional<Member> findMember = memberRepository.findByNickname(updateNickname);
        return findMember.isPresent();
    }
}
