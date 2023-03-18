package com.lastone.core.domain.sbd;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sbd_id")
    private Long id;

    private Long memberId;

    private int deadLift;

    private int benchPress;

    private int squat;


}
