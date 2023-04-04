package com.lastone.core.dto.gym;

import com.lastone.core.util.validator.gym.Coordinate;
import com.lastone.core.util.validator.gym.Location;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class GymDto {

    @NotBlank(message = "헬스장 이름은 필수 입력 값입니다.")
    private String name;

    @Location
    private String location;

    @Coordinate
    private String latitude;

    @Coordinate
    private String longitude;
}
