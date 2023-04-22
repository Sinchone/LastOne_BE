package com.lastone.core.dto.applicaation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ApplicationRequestedDto {

    private Long recruitment_id;
    private String title;
    private String gym;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime startedAt;
    private Long memberId;
    private String profileUrl;
    private String nickName;
    private String gender;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime applicationDate;

    @QueryProjection
    public ApplicationRequestedDto(Long recruitment_id, String title, String gym, LocalDateTime startedAt,
                                   Long memberId, String profileUrl, String nickName,
                                   String gender, LocalDateTime applicationDate) {
        this.recruitment_id = recruitment_id;
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