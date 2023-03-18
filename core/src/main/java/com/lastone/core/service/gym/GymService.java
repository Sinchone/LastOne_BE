package com.lastone.core.service.gym;

import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.repository.gym.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public void findByNameAndLocation(GymDto gym) {
    }
}
