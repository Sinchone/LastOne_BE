package com.lastone.apiserver.service.gym;

import com.lastone.core.dto.gym.GymDto;
import java.util.List;

public interface GymService {

    List<GymDto> findAllByMemberId(Long memberId);

    void updateByMemberId(Long memberId, List<GymDto> gyms);
}
