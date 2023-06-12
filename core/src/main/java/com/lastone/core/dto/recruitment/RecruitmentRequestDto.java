package com.lastone.core.dto.recruitment;

import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.recruitment.WorkoutPart;
import com.lastone.core.dto.gym.GymDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentRequestDto {

    @Valid
    private GymDto gym;

    @NotBlank
    @Size(max = 20)
    private String title;

    @NotBlank
    @Size(max = 100)
    private String description;

    @Valid
    private StartedAtDto startedAt;

    @NotNull
    @Enumerated
    private WorkoutPart workoutPart;

    @NotNull
    @Enumerated
    private PreferGender preferGender;

}
