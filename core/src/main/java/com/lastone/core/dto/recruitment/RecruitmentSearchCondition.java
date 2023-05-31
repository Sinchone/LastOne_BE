package com.lastone.core.dto.recruitment;

import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.recruitment.WorkoutPart;
import com.lastone.core.util.validator.enumerated.Enum;
import com.lastone.core.util.validator.recruitment.RecruitmentDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RecruitmentSearchCondition {

    @Enum(enumClass = WorkoutPart.class)
    private String workoutPart;
    @Enum(enumClass = PreferGender.class)
    private String preferGender;
    @RecruitmentDate
    private String date;
    @Size(max = 20)
    private String title;
    private String gymName;
    private Boolean isRecruiting;
    private Long lastId;

    public RecruitmentSearchCondition() {
        this.isRecruiting = true;
    }

    public LocalDateTime getLocalDateTime() {
        if (!StringUtils.hasText(this.date)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(this.date, formatter).atStartOfDay();
    }

    public WorkoutPart getWorkoutPart() {
        return WorkoutPart.from(this.workoutPart);
    }

    public PreferGender getPreferGender() {
        return PreferGender.from(this.preferGender);
    }
}
