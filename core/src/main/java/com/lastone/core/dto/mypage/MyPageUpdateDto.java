package com.lastone.core.dto.mypage;

import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.gym.GymUpdateDto;
import com.lastone.core.dto.member.MemberUpdateDto;
import com.lastone.core.dto.sbd.SbdDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyPageUpdateDto {

    private MemberUpdateDto member;

    private List<GymDto> gyms;

    private SbdDto sbd;

}
