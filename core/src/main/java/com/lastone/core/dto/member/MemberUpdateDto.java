package com.lastone.core.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class MemberUpdateDto {

    @NotBlank
    @Size(min = 2, max = 15)
    private String nickname;

    @NotBlank
    @Size(min = 1, max = 1)
    private String gender;

    private String profileUrl;

    private String workoutPurpose;

    private String workoutTime;

    private String workoutDay;

    public void setProfileUrl(String url) {
        this.profileUrl = url;
    }

}
