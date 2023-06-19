package com.lastone.core.dto.applicaation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lastone.core.domain.application.ApplicationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@ToString
public class ApplicationDto {

    private final Long applicationId;

    private final Long applicantId;

    private final String nickname;

    private final String profileUrl;

    private final String gender;

    private ApplicationStatus status;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime applicationDate;

    @QueryProjection
    public ApplicationDto(Long applicationId, Long applicantId, String nickname, String profileUrl, String gender, ApplicationStatus status, LocalDateTime applicationDate) {
        this.applicationId = applicationId;
        this.applicantId = applicantId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.gender = gender;
        this.status = status;
        this.applicationDate = applicationDate;
    }
}