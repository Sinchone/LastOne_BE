package com.lastone.apiserver.service.gym;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member_gym.MemberGym;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.mapper.mapper.GymMapper;
import com.lastone.core.repository.gym.GymRepository;
import com.lastone.core.repository.member_gym.MemberGymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GymServiceImpl implements GymService {

    private final GymRepository gymRepository;

    private final MemberGymRepository memberGymRepository;

    private final GymMapper gymMapper;

    public List<GymDto> findAllByMemberId(Long memberId) {
        List<MemberGym> memberGyms = memberGymRepository.findAllByMemberIdAndDelete(memberId);
        List<GymDto> gymDtos = new ArrayList<>();

        for (MemberGym memberGym : memberGyms) {
            GymDto gymDto = gymMapper.toDto(gymRepository.findById(memberGym.getGymId()).orElse(null));
            gymDtos.add(gymDto);
        }
        return gymDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateByMemberId(Long memberId, List<GymDto> gyms) {
        List<Long> gymIds = saveAndGetGymIds(gyms);
        List<MemberGym> memberGyms = memberGymRepository.findAllByMemberIdAndDelete(memberId);
        for (MemberGym memberGym : memberGyms) {
            Long gymId = memberGym.getGymId();
            if (gymIds.contains(gymId)) {
                gymIds.remove(gymId);
                continue;
            }
            memberGym.delete();
        }
        for (Long gymId : gymIds) {
            memberGymRepository.save(MemberGym.builder()
                    .memberId(memberId)
                    .gymId(gymId)
                    .build());
        }
    }

    private List<Long> saveAndGetGymIds(List<GymDto> gyms) {
        List<Long> gymIds = new ArrayList<>();
        for (GymDto gym : gyms) {
            gymIds.add(saveGym(gym));
        }
        return gymIds;
    }

    private Long saveGym(GymDto gymDto) {
        Gym gym = gymRepository.findByGymDto(gymDto);
        if (gym == null) {
            gym = gymRepository.save(gymMapper.toEntity(gymDto));
        }
        return gym.getId();
    }
}
