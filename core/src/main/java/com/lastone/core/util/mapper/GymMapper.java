package com.lastone.core.util.mapper;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.gym.GymDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GymMapper extends GenericMapper<GymDto, Gym> {

    @Override
    GymDto toDto(Gym gym);

    @Override
    List<GymDto> toDto(List<Gym> e);
}
