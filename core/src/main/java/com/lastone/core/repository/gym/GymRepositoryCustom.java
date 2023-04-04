package com.lastone.core.repository.gym;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.dto.gym.GymDto;

public interface GymRepositoryCustom {
    Gym findByGymDto(GymDto gymDto);
}
