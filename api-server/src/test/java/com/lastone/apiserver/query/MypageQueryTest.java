package com.lastone.apiserver.query;

import com.lastone.apiserver.service.mypage.MyPageService;
import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.mypage.MyPageDto;
import com.lastone.core.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("api-local")
@SpringBootTest
public class MypageQueryTest {

    @Autowired
    private MyPageService myPageService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void MyPageInquireTest() {
        long startTime = System.currentTimeMillis();
        String email = "test9340f70f-fa0f-11ed-957a-005056c00001";
        Member member = memberRepository.findByEmail(email).orElse(null);
        MyPageDto myPage = myPageService.getMyPage(member.getId());
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Execution time: " + executionTime + " milliseconds");
    }
}
