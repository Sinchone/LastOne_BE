package com.lastone.core.domain.gym;

import com.lastone.core.repository.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gym extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gym_id")
    private Long id;

    private String name;

    private String location;

    private String latitude;

    private String longitude;




}
