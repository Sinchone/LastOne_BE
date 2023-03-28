package com.lastone.core.dto.sbd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class SbdDto {

    @Min(value = 0)
    @Max(value = 999)
    private int deadLift;

    @Min(value = 0)
    @Max(value = 999)
    private int benchPress;

    @Min(value = 0)
    @Max(value = 999)
    private int squat;
}
