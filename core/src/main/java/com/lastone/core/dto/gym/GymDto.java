package com.lastone.core.dto.gym;

import com.lastone.core.util.validator.gym.Coordinate;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GymDto {

    @NotBlank(message = "헬스장 이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "헬스장 지역은 필수 입력 값입니다.")
    private String location;

    @Coordinate
    private String latitude;

    @Coordinate
    private String longitude;
}
