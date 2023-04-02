package com.lastone.apiserver.service.member;

import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.member.MemberUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {

    public MemberDto findById(Long memberId);

    public void update(Long memberId, MemberUpdateDto memberUpdateDto, MultipartFile profileImg) throws IOException;

    public void isExist(Long memberId);


}
