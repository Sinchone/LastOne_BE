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

    private final Long id;

    private final String title;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private final LocalDateTime startedAt;

    private final String gym;

    private List<ApplicationDto> applications;

    @QueryProjection
    public ApplicationReceivedDto(Long id,String title, LocalDateTime startedAt, String gym) {
        this.id = id;
        this.title = title;
        this.startedAt = startedAt;
        this.gym = gym;
    }

    public void includeApplicants(List<ApplicationDto> applications) {
        this.applications = applications;
    }
}
