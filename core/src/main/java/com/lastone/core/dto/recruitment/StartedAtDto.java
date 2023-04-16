package com.lastone.core.dto.recruitment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StartedAtDto {

    private String date;

    private Meridiem meridiem;

    private String time;
}