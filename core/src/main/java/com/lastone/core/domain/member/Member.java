package com.lastone.core.domain.member;

import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.repository.BaseTime;
import io.netty.util.internal.StringUtil;
import lombok.*;
import org.springframework.util.StringUtils;

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

    private String workoutPurpose;

    private String workoutTime;

    private String workoutDay;

    private String status;

    public void updateMember(MemberDto memberDto) {
        if (StringUtils.hasText(memberDto.getNickname())) {
            this.nickname = memberDto.getNickname();
        }
        if (StringUtils.hasText(memberDto.getGender())) {
            this.gender = memberDto.getGender();
        }
        this.workoutPurpose = memberDto.getWorkoutPurpose();
        this.workoutTime = memberDto.getWorkoutTime();
        this.workoutDay = memberDto.getWorkoutDay();
    }
}
