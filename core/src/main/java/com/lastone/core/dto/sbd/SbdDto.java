package com.lastone.core.dto.sbd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SbdDto {

    private Long id;

    private int deadLift;

    private int benchPress;

    private int squat;
}
