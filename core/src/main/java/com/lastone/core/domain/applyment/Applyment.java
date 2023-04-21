package com.lastone.core.domain.applyment;

import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.repository.BaseTime;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applyment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applyment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member applyer;

    @Enumerated(EnumType.STRING)
    private ApplymentStatus status;

    public Applyment(Recruitment recruitment, Member applyer) {
        this.recruitment = recruitment;
        this.applyer = applyer;
        this.status = ApplymentStatus.WAITING;
    }
}