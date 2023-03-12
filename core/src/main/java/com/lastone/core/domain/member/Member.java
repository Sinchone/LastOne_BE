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

    @Column(name = "gym1_id")
    private Long gym1Id;

    @Column(name = "gym2_id")
    private Long gym2Id;

    private String nickname;

    private String email;

    private String gender;

    private String profileUrl;

    private String workoutPurpose;

    private String workoutTime;

    private String workoutDay;

    private String status;

}
