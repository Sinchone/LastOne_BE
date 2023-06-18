package com.lastone.core.util.mapper;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.dto.gym.GymDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GymMapper extends GenericMapper<GymDto, Gym> {

    @Override
    GymDto toDto(Gym gym);


    @Mapping(target = "id", ignore = true)
    @Override
    Gym toEntity(GymDto dto);
}
