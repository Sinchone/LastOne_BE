package com.lastone.core.dto.mypage;

import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.member.MemberUpdateDto;
import com.lastone.core.dto.sbd.SbdDto;
import lombok.Getter;
import lombok.Setter;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class MyPageUpdateDto {

    @Valid
    @NotNull
    private MemberUpdateDto member;

    @Valid
    @NotNull
    private List<GymDto> gyms;

    @Valid
    private SbdDto sbd;
}
