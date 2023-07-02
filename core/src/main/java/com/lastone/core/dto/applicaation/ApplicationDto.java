package com.lastone.core.dto.applicaation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lastone.core.domain.application.Application;
import com.lastone.core.domain.application.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ApplicationDto {

    private final Long applicationId;

    private final Long applicantId;

    private final String nickname;

    private final String profileUrl;

    private final String gender;

    private ApplicationStatus status;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime applicationDate;

    public static ApplicationDto toDto(Application application) {
        return ApplicationDto.builder()
                .applicantId(application.getId())
                .applicantId(application.getApplicant().getId())
                .nickname(application.getApplicant().getNickname())
                .profileUrl(application.getApplicant().getProfileUrl())
                .gender(application.getApplicant().getGender())
                .status(application.getStatus())
                .applicationDate(application.getCreatedAt())
                .build();
    }
}