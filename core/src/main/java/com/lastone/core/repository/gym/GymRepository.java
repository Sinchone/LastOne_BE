package com.lastone.core.repository.gym;

import com.lastone.core.domain.gym.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long>, GymRepositoryCustom {


}
