package com.lastone.apiserver.service.mypage;

import com.lastone.apiserver.service.gym.GymService;
import com.lastone.apiserver.service.member.MemberService;
import com.lastone.apiserver.service.sbd.SbdService;
import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.mypage.MyPageDto;
import com.lastone.core.dto.mypage.MyPageUpdateDto;
import com.lastone.core.dto.mypage.NicknameCheckDto;
import com.lastone.core.dto.sbd.SbdDto;
import com.lastone.core.util.mapper.MemberMapper;
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

    private final MemberMapper memberMapper;

    @Override
    public NicknameCheckDto isDuplicatedNickname(String nickname) {
        boolean result = memberService.isDuplicateNickname(nickname);
        return new NicknameCheckDto(result);
    }

    public MyPageDto getMyPage(Long memberId) {
        Member member = memberService.findById(memberId);
        return getMyPageDtoByMember(member);
    }

    private MyPageDto getMyPageDtoByMember(Member member) {
        MemberDto memberDto = memberMapper.toDto(member);
        List<GymDto> gymDtoList = gymService.findAllByMember(member);
        SbdDto sbdDto = sbdService.findByMember(member);

        if (sbdDto == null) {
            sbdDto = new SbdDto();
        }
        return new MyPageDto(memberDto, gymDtoList, sbdDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMyPage(Long memberId, MyPageUpdateDto myPageUpdateDto, MultipartFile profileImg) throws IOException {
        Member member = memberService.findById(memberId);
        memberService.update(member, myPageUpdateDto.getMember(), profileImg);
        gymService.updateByMember(member, myPageUpdateDto.getGyms());
        sbdService.updateByMember(member, myPageUpdateDto.getSbd());
    }
}
