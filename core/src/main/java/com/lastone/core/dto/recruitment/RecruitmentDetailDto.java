package com.lastone.core.dto.recruitment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.domain.recruitment_img.RecruitmentImg;
import com.lastone.core.domain.sbd.Sbd;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.sbd.SbdDto;
import lombok.*;
import org.springframework.util.ObjectUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentDetailDto {

    private Long memberId;
    private String nickname;
    private String gender;
    private String profileUrl;
    private String workoutPurpose;
    private SbdDto sbd;
    private GymDto gym;
    private Long recruitmentId;
    private String title;
    private String description;
    private PreferGender preferGender;
    private List<String> imgUrls;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime startedAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime createdAt;


    public static RecruitmentDetailDto toDto(Recruitment recruitment) {
        return RecruitmentDetailDto.builder()
                .recruitmentId(recruitment.getId())
                .title(recruitment.getTitle())
                .description(recruitment.getDescription())
                .preferGender(recruitment.getPreferGender())
                .startedAt(recruitment.getStartedAt())
                .createdAt(recruitment.getCreatedAt())
                .gym(toGymDto(recruitment.getGym()))
                .memberId(recruitment.getMember().getId())
                .nickname(recruitment.getMember().getNickname())
                .profileUrl(recruitment.getMember().getProfileUrl())
                .gender(recruitment.getMember().getGender())
                .workoutPurpose(recruitment.getMember().getWorkoutPurpose())
                .imgUrls(toImgUrls(recruitment.getRecruitmentImgs()))
                .build();
    }

    private static GymDto toGymDto(Gym gym) {
        return GymDto.builder()
                .name(gym.getName())
                .location(gym.getLocation())
                .longitude(gym.getLongitude())
                .latitude(gym.getLatitude())
                .build();
    }

    private static List<String> toImgUrls(List<RecruitmentImg> recruitmentImgs) {
        return recruitmentImgs.stream()
                .map(RecruitmentImg::getImgUrl)
                .collect(Collectors.toList());
    }


    public void setSbdDto(Sbd sbd) {
        if (ObjectUtils.isEmpty(sbd)) {
            return;
        }
        this.sbd = SbdDto.builder()
                .squat(sbd.getSquat())
                .deadLift(sbd.getDeadLift())
                .benchPress(sbd.getBenchPress())
                .build();
    }
}
