package com.lastone.core.dto.gym;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class GymDto {

    private Long id;

    private String name;

    private String location;

    private String latitude;

    private String longitude;
}
