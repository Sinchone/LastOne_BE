package com.lastone.core.domain.member_gym;

import com.lastone.core.repository.BaseTime;
import com.lastone.core.util.BooleanToYNConverter;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Table(name = "member_gym")
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

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long gymId;

    @Convert(converter = BooleanToYNConverter.class)
    @ColumnDefault("false")
    private boolean isDeleted;

    public void changeGymId(Long id) {
        this.gymId = id;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
