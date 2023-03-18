package com.lastone.core.util.mapper;

import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.member.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;



@Mapper(componentModel = "spring")
public interface MemberMapper extends GenericMapper<MemberDto, Member>{

    @Mapping(target = "gyms", ignore = true)
    @Override
    MemberDto toDto(Member member);

    @Override
    List<MemberDto> toDto(List<Member> e);
}
