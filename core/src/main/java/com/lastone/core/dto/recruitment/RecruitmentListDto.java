package com.lastone.core.dto.recruitment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.domain.recruitment.WorkoutPart;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecruitmentListDto {

    private Long id;

    private String title;

    private String gym;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime startedAt;

    private RecruitmentStatus status;

    private PreferGender preferGender;

    private WorkoutPart workoutPart;


    @QueryProjection
    public RecruitmentListDto(Long id, String title, String gym, LocalDateTime startedAt, RecruitmentStatus status, PreferGender preferGender, WorkoutPart workoutPart) {
        this.id = id;
        this.title = title;
        this.gym = gym;
        this.startedAt = startedAt;
        this.status = status;
        this.preferGender = preferGender;
        this.workoutPart = workoutPart;
    }
}
