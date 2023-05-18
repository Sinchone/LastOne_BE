package com.lastone.core.dto.recruitment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.sbd.Sbd;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.sbd.SbdDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentDetailDto {

    private Long memberId;
    private String nickname;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime startedAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd 'T' HH:mm")
    private LocalDateTime createdAt;

    @QueryProjection
    public RecruitmentDetailDto(Long memberId, String nickname, String profileUrl,
                                String workoutPurpose, Gym gym, Long recruitmentId,
                                String title, String description, PreferGender preferGender,
                                LocalDateTime startedAt, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.workoutPurpose = workoutPurpose;
        this.gym = buildGymDto(gym);
        this.recruitmentId = recruitmentId;
        this.title = title;
        this.description = description;
        this.preferGender = preferGender;
        this.startedAt = startedAt;
        this.createdAt = createdAt;
    }

    private GymDto buildGymDto(Gym gym) {
        return GymDto.builder()
                .name(gym.getName())
                .location(gym.getLocation())
                .longitude(gym.getLongitude())
                .latitude(gym.getLatitude())
                .build();
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
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
