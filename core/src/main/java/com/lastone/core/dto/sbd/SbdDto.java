package com.lastone.core.dto.sbd;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SbdDto {

    @Min(value = 0)
    @Max(value = 999)
    private Integer deadLift;

    @Min(value = 0)
    @Max(value = 999)
    private Integer benchPress;

    @Min(value = 0)
    @Max(value = 999)
    private Integer squat;
}
