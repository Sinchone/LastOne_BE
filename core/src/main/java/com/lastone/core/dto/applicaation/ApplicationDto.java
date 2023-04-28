package com.lastone.core.dto.applicaation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@ToString
public class ApplicationDto {

    private final Long applicationId;
    private final Long applicantId;
    private final String nickName;
    private final String profileUr;
    private final String gender;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime applicationDate;

    @QueryProjection
    public ApplicationDto(Long applicationId, Long applicantId, String nickName, String profileUr, String gender, LocalDateTime applicationDate) {
        this.applicationId = applicationId;
        this.applicantId = applicantId;
        this.nickName = nickName;
        this.profileUr = profileUr;
        this.gender = gender;
        this.applicationDate = applicationDate;
    }
}