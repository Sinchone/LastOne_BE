package com.lastone.core.domain.gym;

import com.lastone.core.repository.BaseTime;
import lombok.*;
import javax.persistence.*;

@ToString
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gym extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gym_id")
    private Long id;

    private String name;

    private String location;

    private String latitude;

    private String longitude;
}
