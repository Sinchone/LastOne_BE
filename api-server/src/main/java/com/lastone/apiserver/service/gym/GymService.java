package com.lastone.apiserver.service.gym;

import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.gym.GymDto;
import java.util.List;

public interface GymService {

    List<GymDto> findAllByMember(Member member);

    void updateByMember(Member member, List<GymDto> gyms);
}
