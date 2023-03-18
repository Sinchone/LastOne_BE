package com.lastone.core.dto.mypage;

import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.sbd.SbdDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class MyPageDto {

    private MemberDto member;

    private SbdDto sbd;

    private GymDto gym1;

    private GymDto gym2;

    public void registerGym(GymDto gym) {
        log.info("헬스장 등록");
        log.info("gym = {}", gym);
        if (gym1 != null && gym2 != null) {
            throw new IllegalArgumentException("헬스장은 최대 2개 까지 등록 가능합니다.");
        }
        if (gym1 == null) {
            gym1 = gym;
            return;
        }
        gym2 = gym;
    }
}
