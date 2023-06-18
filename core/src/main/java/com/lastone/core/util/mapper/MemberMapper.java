package com.lastone.core.util.mapper;

import com.lastone.core.domain.member.Day;
import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.member.MemberDto;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@Mapper(componentModel = "spring")
public interface MemberMapper extends GenericMapper<MemberDto, Member> {


    @Mapping(target = "workoutDay", source = "workoutDay", qualifiedByName = "toWorkoutDayAsList")
    @Override
    MemberDto toDto(Member member);

    @Mapping(target = "workoutDay", source = "workoutDay", qualifiedByName = "toWorkoutDayListAsString")
    @Override
    Member toEntity(MemberDto dto);

    @Override
    List<MemberDto> toDto(List<Member> e);

    @Named("toWorkoutDayAsList")
    default List<String> toWorkoutDaysList(String workoutDay) {
        if (StringUtils.isBlank(workoutDay)) {
            return new ArrayList<>();
        }
        return Arrays.asList(workoutDay.split(" "));
    }

    @Named("toWorkoutDayListAsString")
    default String toWorkoutDayListAsString(List<String> workoutDays) {
        return Day.sortListToString(workoutDays);
    }
}
