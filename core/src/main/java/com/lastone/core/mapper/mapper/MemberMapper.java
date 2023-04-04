package com.lastone.core.mapper.mapper;

import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.member.MemberDto;
import org.mapstruct.Mapper;

import java.util.List;



@Mapper(componentModel = "spring")
public interface MemberMapper extends GenericMapper<MemberDto, Member> {


    @Override
    MemberDto toDto(Member member);

    @Override
    List<MemberDto> toDto(List<Member> e);
}
