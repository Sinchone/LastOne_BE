package com.lastone.core.dto.recruitment;

import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.recruitment.WorkoutPart;
import com.lastone.core.dto.gym.GymDto;
import lombok.*;

import javax.validation.Valid;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentCreateDto {

    @Valid
    private GymDto gym;

    private String title;

    private String description;

    private StartedAtDto startedAt;

    private WorkoutPart workoutPart;

    private PreferGender preferGender;

}
