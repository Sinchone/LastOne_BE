package com.lastone.apiserver.mypage;

import com.lastone.core.domain.member.MemberDto;
import com.lastone.core.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

    private final MemberService memberService;

    public MyPageDto getMyPageDto(String email) {

        return new MyPageDto();


    }


}
