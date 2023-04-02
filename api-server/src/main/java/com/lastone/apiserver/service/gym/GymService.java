package com.lastone.apiserver.service.gym;

import com.lastone.core.dto.gym.GymDto;

import java.util.List;

public interface GymService {

    public List<GymDto> findAllByMemberId(Long memberId);

    public void updateByMemberId(List<GymDto> gyms, Long memberId);
}
