package com.lastone.core.domain.sbd;

import com.lastone.core.domain.member.Member;
import com.lastone.core.repository.BaseTime;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Sbd extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sbd_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int deadLift;

    private int benchPress;

    private int squat;

    public boolean isEqualTo(Sbd sbd) {
        if (this.deadLift == sbd.deadLift && this.benchPress == sbd.benchPress && this.squat == sbd.squat) {
            return true;
        }
        return false;
    }

    public void assignMember(Member member) {
        this.member = member;
    }
}
