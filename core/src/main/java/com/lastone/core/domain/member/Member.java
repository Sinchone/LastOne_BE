package com.lastone.core.domain.member;

import com.lastone.core.domain.BaseTime;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String email;

    private String gender;

    private String profileUrl;

    private int deadLift;

    private int benchPress;

    private int squat;

    private String workoutPurpose;

    private String workoutTime;

    private String workoutDay;

    private String status;
}
