package com.lastone.core.dto.applicaation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class ApplicationReceivedDto {
    private Long id;
    private String title;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime startedAt;
    private String gym;
    private List<ApplicantDto> applicants;

    @QueryProjection
    public ApplicationReceivedDto(Long id,String title, LocalDateTime startedAt, String gym) {
        this.id = id;
        this.title = title;
        this.startedAt = startedAt;
        this.gym = gym;
    }

    public void includeApplicants(List<ApplicantDto> applicants) {
        this.applicants = applicants;
    }
}
