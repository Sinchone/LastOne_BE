package com.lastone.core.dto.mypage;

import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.sbd.SbdDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MyPageDto {

    private MemberDto member;

    private List<GymDto> gyms;

    private SbdDto sbd;

}
