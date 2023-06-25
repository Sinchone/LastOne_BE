package com.lastone.core.dto.member;

import com.lastone.core.util.validator.member.WorkoutDay;
import com.lastone.core.util.validator.member.WorkoutTime;
import lombok.*;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateDto {

    @NotBlank
    @Size(min = 2, max = 15)
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^(남성|여성)$", message = "성별은 남성 또는 여성만 가능합니다.")
    private String gender;

    @Size(max = 20, message = "운동 목표는 최대 20자 까지 작성 가능합니다.")
    private String workoutPurpose;

    @WorkoutTime
    private String workoutTime;

    @WorkoutDay
    private List<String> workoutDay;

    private String profileUrl;

    public void setProfileUrl(String url) {
        this.profileUrl = url;
    }
}
