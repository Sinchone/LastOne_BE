package com.lastone.core.domain.member_gym;

import com.lastone.core.domain.BaseTime;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberGym extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_gym_id")
    private Long id;

    private Long memberId;

    private Long gymId;


}
