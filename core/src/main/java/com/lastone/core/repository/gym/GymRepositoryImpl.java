package com.lastone.core.repository.gym;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.dto.gym.GymDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.lastone.core.domain.gym.QGym.gym;


@RequiredArgsConstructor
public class GymRepositoryImpl implements  GymRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public Gym findByGymDto(GymDto gymDto) {

        return queryFactory
                .select(gym)
                .from(gym)
                .where(gym.name.eq(gymDto.getName())
                        .and(gym.location.eq(gymDto.getLocation()))
                        .and(gym.latitude.eq(gymDto.getLatitude()))
                        .and(gym.longitude.eq(gymDto.getLongitude())))
                .fetchOne();
    }
}
