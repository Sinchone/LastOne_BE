package com.lastone.core.mapper.mapper;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.dto.gym.GymDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GymMapper extends GenericMapper<GymDto, Gym> {


    @Override
    GymDto toDto(Gym gym);

    @Override
    List<GymDto> toDto(List<Gym> e);
}
