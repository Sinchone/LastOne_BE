package com.lastone.core.dto.gym;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class GymDto {

    private String name;

    private String location;

    private String latitude;

    private String longitude;
}
