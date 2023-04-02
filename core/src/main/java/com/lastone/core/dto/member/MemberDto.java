package com.lastone.core.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lastone.core.dto.gym.GymDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Convert;

@Getter
@Builder
@AllArgsConstructor
public class MemberDto {

    private Long id;

    private String nickname;

    private String email;

    private String gender;

    private String profileUrl;

    private String workoutPurpose;

    private String workoutTime;

    private String workoutDay;

    private String status;

    private Boolean isEdited;

}
