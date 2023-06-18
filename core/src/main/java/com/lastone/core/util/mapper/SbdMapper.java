package com.lastone.core.util.mapper;

import com.lastone.core.domain.sbd.Sbd;
import com.lastone.core.dto.sbd.SbdDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SbdMapper extends GenericMapper<SbdDto, Sbd> {

    @Override
    SbdDto toDto(Sbd sbd);

    @Override
    List<SbdDto> toDto(List<Sbd> e);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Override
    Sbd toEntity(SbdDto dto);
}
