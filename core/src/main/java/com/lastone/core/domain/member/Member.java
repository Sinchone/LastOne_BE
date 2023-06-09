package com.lastone.core.domain.member;

import com.lastone.core.domain.notification.Notification;
import com.lastone.core.dto.member.MemberUpdateDto;
import com.lastone.core.repository.BaseTime;
import com.lastone.core.util.BooleanToYNConverter;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    private String gender;

    private String profileUrl;

    private String workoutPurpose;

    private String workoutTime;

    private String workoutDay;

    private String status;

    @OneToMany(mappedBy = "member")
    private List<Notification> notificationList = new ArrayList<>();

    @Convert(converter = BooleanToYNConverter.class)
    @ColumnDefault("false")
    private Boolean isEdited;

    public void update(MemberUpdateDto memberUpdateDto) {
        this.nickname = memberUpdateDto.getNickname();
        this.gender = memberUpdateDto.getGender();
        this.profileUrl = memberUpdateDto.getProfileUrl();
        this.workoutPurpose = memberUpdateDto.getWorkoutPurpose();
        this.workoutTime = memberUpdateDto.getWorkoutTime();
        this.workoutDay = Day.sortListToString(memberUpdateDto.getWorkoutDay());
        this.isEdited = true;
    }

    public void initNickname(String nickname) {
        this.nickname = nickname;
    }
}
