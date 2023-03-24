package com.lastone.core.mapper.mapper;

import com.lastone.core.domain.sbd.Sbd;
import com.lastone.core.dto.sbd.SbdDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SbdMapper extends GenericMapper<SbdDto, Sbd> {

    @Override
    SbdDto toDto(Sbd sbd);

    @Override
    List<SbdDto> toDto(List<Sbd> e);

    @Override
    Sbd toEntity(SbdDto dto);
}
