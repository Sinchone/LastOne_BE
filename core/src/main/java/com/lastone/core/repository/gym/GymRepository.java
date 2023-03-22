package com.lastone.core.repository.gym;

import com.lastone.core.domain.gym.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GymRepository extends JpaRepository<Gym, Long> {

    @Query("")
    Gym findByGymDto(Gym gym1);
}
