package com.lastone.apiserver.service.mypage;

import com.lastone.apiserver.service.gym.GymService;
import com.lastone.apiserver.service.member.MemberService;
import com.lastone.apiserver.service.sbd.SbdService;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.mypage.MyPageDto;
import com.lastone.core.dto.mypage.MyPageUpdateDto;
import com.lastone.core.dto.sbd.SbdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberService memberService;

    private final GymService gymService;

    private final SbdService sbdService;

    public MyPageDto getMyPage(Long memberId) {
        MemberDto member = memberService.findById(memberId);
        List<GymDto> gyms = gymService.findAllByMemberId(memberId);
        SbdDto sbd = sbdService.findByMemberId(memberId);

        return MyPageDto.builder()
                .member(member)
                .gyms(gyms)
                .sbd(sbd)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMyPage(Long memberId, MyPageUpdateDto myPageUpdateDto, MultipartFile profileImg) throws IOException {
        memberService.update(memberId, myPageUpdateDto.getMember(), profileImg);
        gymService.updateByMemberId(memberId, myPageUpdateDto.getGyms());
        sbdService.updateByMemberId(memberId, myPageUpdateDto.getSbd());
    }
}
