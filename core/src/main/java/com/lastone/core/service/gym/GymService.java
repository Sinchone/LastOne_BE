package com.lastone.core.service.gym;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member_gym.MemberGym;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.mapper.mapper.GymMapper;
import com.lastone.core.repository.gym.GymRepository;
import com.lastone.core.repository.member_gym.MemberGymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    private final MemberGymRepository memberGymRepository;

    private final GymMapper gymMapper;

    public void findByNameAndLocation(GymDto gym) {
    }

    public List<GymDto> findAllByMemberId(Long memberId) {
        List<MemberGym> memberGyms = memberGymRepository.findAllByMemberId(memberId);
        List<GymDto> gymDtos = new ArrayList<>();

        for (MemberGym memberGym : memberGyms) {
            GymDto gymDto = gymMapper.toDto(gymRepository.findById(memberGym.getGymId()).orElse(null));
            gymDtos.add(gymDto);
        }

        return gymDtos;
    }

    public void updateByMemberId(List<GymDto> gyms, Long memberId) {
        List<MemberGym> memberGyms = memberGymRepository.findAllByMemberId(memberId);

        for (int i=0; i<2; i++) {
            MemberGym memberGym = memberGyms.get(i);
            Gym gym = gymRepository.findByGymDto(gyms.get(i));
            if (gym == null) {
                gym = gymRepository.save(gymMapper.toEntity(gyms.get(i)));
            }
            memberGym.changeGymId(gym.getId());
        }
    }
}
