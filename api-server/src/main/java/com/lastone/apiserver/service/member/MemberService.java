package com.lastone.apiserver.service.member;

import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.member.MemberUpdateDto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface MemberService {

    boolean isDuplicateNickname(String nickname);

    Member findById(Long memberId);

    void update(Member member, MemberUpdateDto memberUpdateDto, MultipartFile profileImg) throws IOException;

}
