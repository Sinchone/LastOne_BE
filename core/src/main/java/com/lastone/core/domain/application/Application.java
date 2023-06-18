package com.lastone.core.domain.application;

import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.repository.BaseTime;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member applicant;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public Application(Recruitment recruitment, Member applicant) {
        this.recruitment = recruitment;
        this.applicant = applicant;
        this.status = ApplicationStatus.WAITING;
    }

    public void fail() {
        this.status = ApplicationStatus.FAILURE;
    }

    public void cancel() {
        this.status = ApplicationStatus.CANCEL;
    }
}