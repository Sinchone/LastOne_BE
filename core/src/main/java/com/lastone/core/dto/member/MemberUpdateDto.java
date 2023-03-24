package com.lastone.core.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class MemberUpdateDto {

    private Long id;

    private String nickname;

    private String gender;

    private String profileUrl;

    private String workoutPurpose;

    private String workoutTime;

    private String workoutDay;

    public void setProfileUrl(String url) {
        this.profileUrl = url;
    }

}
