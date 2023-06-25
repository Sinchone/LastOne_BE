package com.lastone.core.dto.recruitment;

import com.lastone.core.util.validator.recruitment.RecruitmentCreateDate;
import com.lastone.core.util.validator.recruitment.RecruitmentTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class StartedAtDto {

    @NotBlank
    @RecruitmentCreateDate
    private String date;

    @NotNull
    @Enumerated
    private Meridiem meridiem;

    @NotBlank
    @RecruitmentTime
    private String time;

    public String toTime() {
        StringBuffer stringBuffer = new StringBuffer();
        return stringBuffer
                .append(date)
                .append(" ")
                .append(meridiem.getText())
                .append(" ")
                .append(time)
                .toString();
    }
}