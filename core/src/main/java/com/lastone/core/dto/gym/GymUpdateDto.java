package com.lastone.core.dto.gym;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GymUpdateDto {
    List<GymDto> gyms;
}
