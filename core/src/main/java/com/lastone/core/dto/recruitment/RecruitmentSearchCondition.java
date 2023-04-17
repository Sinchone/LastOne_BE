package com.lastone.core.dto.recruitment;

import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.recruitment.WorkoutPart;
import com.lastone.core.util.validator.recruitment.RecruitmentDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class RecruitmentSearchCondition {

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 9;

    @Enumerated
    private WorkoutPart workoutPart;
    @Enumerated
    private PreferGender preferGender;
    @RecruitmentDate
    private String date;
    @Size(max = 20)
    private String title;
    private String gymName;
    private Boolean isRecruiting;
    private Boolean isNewest;
    private int offset;
    private int limit;

    public RecruitmentSearchCondition() {
        this.offset = DEFAULT_OFFSET;
        this.limit = DEFAULT_LIMIT;
        this.isRecruiting = true;
        this.isNewest = false;
    }

    public LocalDateTime getLocalDateTime() {
        if (ObjectUtils.isEmpty(this.date)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(this.date, formatter).atStartOfDay();
    }
}
