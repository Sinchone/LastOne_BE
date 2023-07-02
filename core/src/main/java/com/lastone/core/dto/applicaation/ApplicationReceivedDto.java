package com.lastone.core.dto.applicaation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lastone.core.domain.recruitment.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ApplicationReceivedDto {

    private final Long id;

    private final String title;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime startedAt;

    private final String gym;

    private List<ApplicationDto> applications;

    public static ApplicationReceivedDto toDto(Recruitment recruitment) {
        return ApplicationReceivedDto.builder()
                .id(recruitment.getId())
                .title(recruitment.getTitle())
                .startedAt(recruitment.getStartedAt())
                .gym(recruitment.getGym().getName())
                .applications(recruitment.getApplications().stream().map(ApplicationDto::toDto).collect(Collectors.toList()))
                .build();
    }
}
