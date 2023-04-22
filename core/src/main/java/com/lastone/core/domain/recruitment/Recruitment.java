package com.lastone.core.domain.recruitment;

import com.lastone.core.domain.applyment.Applyment;
import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment_img.RecruitmentImg;
import com.lastone.core.dto.recruitment.RecruitmentRequestDto;
import com.lastone.core.repository.BaseTime;
import com.lastone.core.util.BooleanToYNConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recruitment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RecruitmentImg> recruitmentImgs = new ArrayList<>();

    @OneToMany(mappedBy = "recruitment")
    private List<Applyment> applyments = new ArrayList<>();

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

    public void setImgFiles(List<RecruitmentImg> recruitmentImgs) {
        for (RecruitmentImg recruitmentImg : recruitmentImgs) {
            recruitmentImg.setRecruitment(this);
            this.recruitmentImgs.add(recruitmentImg);
        }
    }

    public void update(RecruitmentRequestDto recruitmentUpdateDto) {
        workoutPart = recruitmentUpdateDto.getWorkoutPart();
        title = recruitmentUpdateDto.getTitle();
        description = recruitmentUpdateDto.getDescription();
        preferGender = recruitmentUpdateDto.getPreferGender();
    }

    public void updateGym(Gym gym) {
        this.gym = gym;
    }

    public void updateStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void updateImg(List<RecruitmentImg> recruitmentImgs) {
        this.recruitmentImgs.clear();
        setImgFiles(recruitmentImgs);
    }

    public void delete() {
        this.recruitmentImgs.clear();
        this.isDeleted = true;
    }
}
