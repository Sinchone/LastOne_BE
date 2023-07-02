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
@ToString
@Builder
@AllArgsConstructor
public class ApplicationRequestedDto {

    private final Long applicationId;

    private final Long recruitmentId;

    private final String title;

    private final String gym;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime startedAt;

    private final Long memberId;

    private final String profileUrl;

    private final String nickname;

    private final String gender;

    private final ApplicationStatus status;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime applicationDate;

    public static ApplicationRequestedDto toDto(Application application) {
        return ApplicationRequestedDto.builder()
                .applicationId(application.getId())
                .status(application.getStatus())
                .applicationDate(application.getCreatedAt())
                .recruitmentId(application.getRecruitment().getId())
                .title(application.getRecruitment().getTitle())
                .gym(application.getRecruitment().getGym().getName())
                .startedAt(application.getRecruitment().getStartedAt())
                .memberId(application.getApplicant().getId())
                .profileUrl(application.getApplicant().getProfileUrl())
                .nickname(application.getApplicant().getNickname())
                .gender(application.getApplicant().getGender())
                .build();
    }
}