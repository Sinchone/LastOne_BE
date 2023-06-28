package com.lastone.core.dto.recruitment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.domain.recruitment.WorkoutPart;
import com.lastone.core.domain.recruitment_img.RecruitmentImg;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class RecruitmentListDto {

    private Long id;

    private String title;

    private String gym;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime startedAt;

    private RecruitmentStatus status;

    private PreferGender preferGender;

    private WorkoutPart workoutPart;

    private String imgUrl;


    public static List<RecruitmentListDto> toDto(List<Recruitment> recruitments, boolean isMainPage) {
        List<RecruitmentListDto> recruitmentListDtos = new ArrayList<>();
        for (Recruitment recruitment : recruitments) {
            RecruitmentListDto recruitmentListDto = RecruitmentListDto.builder()
                    .id(recruitment.getId())
                    .gym(recruitment.getGym().getName())
                    .status(recruitment.getRecruitmentStatus())
                    .title(recruitment.getTitle())
                    .preferGender(recruitment.getPreferGender())
                    .imgUrl(chooseImgType(recruitment, isMainPage))
                    .startedAt(recruitment.getStartedAt())
                    .workoutPart(recruitment.getWorkoutPart())
                    .build();
            recruitmentListDtos.add(recruitmentListDto);
        }
        return recruitmentListDtos;
    }

    private static String chooseImgType(Recruitment recruitment, boolean isMainPage) {
        if (isMainPage) {
            return toImgUrlForMainPage(recruitment);
        }
        return toImgUrlForListPage(recruitment);
    }

    private static String toImgUrlForListPage(Recruitment recruitment) {
        List<RecruitmentImg> recruitmentImgs = recruitment.getRecruitmentImgs();
        if (recruitmentImgs.isEmpty()) {
            return recruitment.getWorkoutPart().getListDefaultImgUrl();
        }
        return recruitmentImgs.get(0).getImgUrl();
    }

    private static String toImgUrlForMainPage(Recruitment recruitment) {
        List<RecruitmentImg> recruitmentImgs = recruitment.getRecruitmentImgs();
        if (recruitmentImgs.isEmpty()) {
            return recruitment.getWorkoutPart().getMainDefaultImgUrl();
        }
        return recruitmentImgs.get(0).getImgUrl();
    }
}
