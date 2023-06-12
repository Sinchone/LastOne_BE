package com.lastone.core.domain.recruitment;

import com.lastone.core.domain.application.Application;
import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment_img.RecruitmentImg;
import com.lastone.core.dto.recruitment.RecruitmentRequestDto;
import com.lastone.core.dto.recruitment.StartedAtDto;
import com.lastone.core.repository.BaseTime;
import com.lastone.core.util.BooleanToYNConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;


    @Builder.Default
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitmentImg> recruitmentImgs = new ArrayList<>();

    @OneToMany(mappedBy = "recruitment")
    private List<Application> applications = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private WorkoutPart workoutPart;

    private String title;

    private String description;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime startedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RecruitmentStatus recruitmentStatus;
    @Enumerated(EnumType.STRING)
    private PreferGender preferGender;

    @Convert(converter = BooleanToYNConverter.class)
    @ColumnDefault("false")
    private boolean isDeleted;

    public static Recruitment create(Member member, Gym gym, RecruitmentRequestDto recruitmentRequestDto) {
        return Recruitment.builder()
                .member(member)
                .gym(gym)
                .title(recruitmentRequestDto.getTitle())
                .workoutPart(recruitmentRequestDto.getWorkoutPart())
                .description(recruitmentRequestDto.getDescription())
                .startedAt(startedAtToLocalDateTime(recruitmentRequestDto.getStartedAt()))
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .preferGender(recruitmentRequestDto.getPreferGender())
                .build();
    }

    private static LocalDateTime startedAtToLocalDateTime(StartedAtDto startedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd a h:mm", Locale.KOREAN);
        return LocalDateTime.parse(startedAt.toTime(), formatter);
    }

    public void update(RecruitmentRequestDto recruitmentRequestDto) {
        workoutPart = recruitmentRequestDto.getWorkoutPart();
        title = recruitmentRequestDto.getTitle();
        description = recruitmentRequestDto.getDescription();
        preferGender = recruitmentRequestDto.getPreferGender();
        startedAt = startedAtToLocalDateTime(recruitmentRequestDto.getStartedAt());
    }

    public void setImgFiles(List<RecruitmentImg> recruitmentList) {
        recruitmentList.forEach(recruitmentImg -> {
            recruitmentImg.setRecruitment(this);
            this.recruitmentImgs.add(recruitmentImg);
        });
    }

    public void updateGym(Gym gym) {
        this.gym = gym;
    }

    public void updateImg(List<RecruitmentImg> recruitmentImgList) {
        this.recruitmentImgs.clear();
        setImgFiles(recruitmentImgList);
    }

    public void delete() {
        this.recruitmentImgs.clear();
        this.isDeleted = true;
    }

    public void completeMatching() {
        this.recruitmentStatus = RecruitmentStatus.COMPLETE;
    }

    public void cancelMatching() {
        this.recruitmentStatus = RecruitmentStatus.RECRUITING;
    }
}
