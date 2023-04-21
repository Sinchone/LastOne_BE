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
public class ApplicantDto {
    private Long id;
    private String nickName;
    private String profileUr;
    private String gender;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime applicationDate;

    @QueryProjection
    public ApplicantDto(Long id, String nickName, String profileUr, String gender, LocalDateTime applicationDate) {
        this.id = id;
        this.nickName = nickName;
        this.profileUr = profileUr;
        this.gender = gender;
        this.applicationDate = applicationDate;
    }
}