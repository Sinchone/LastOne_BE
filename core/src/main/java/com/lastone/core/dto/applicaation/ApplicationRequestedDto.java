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
public class ApplicationRequestedDto {

    private final Long applicationId;
    private final Long recruitmentId;
    private final String title;
    private final String gym;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private final LocalDateTime startedAt;
    private final Long memberId;
    private final String profileUrl;
    private final String nickName;
    private final String gender;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime applicationDate;

    @QueryProjection
    public ApplicationRequestedDto(Long applicationId, Long recruitmentId, String title, String gym,
                                   LocalDateTime startedAt, Long memberId, String profileUrl,
                                   String nickName, String gender, LocalDateTime applicationDate) {
        this.applicationId = applicationId;
        this.recruitmentId = recruitmentId;
        this.title = title;
        this.gym = gym;
        this.startedAt = startedAt;
        this.memberId = memberId;
        this.profileUrl = profileUrl;
        this.nickName = nickName;
        this.gender = gender;
        this.applicationDate = applicationDate;
    }
}