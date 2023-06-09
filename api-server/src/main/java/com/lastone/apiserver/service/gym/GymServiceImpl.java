package com.lastone.apiserver.service.gym;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member.Member;
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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GymServiceImpl implements GymService {

    private final GymRepository gymRepository;

    private final MemberGymRepository memberGymRepository;

    private final GymMapper gymMapper;

    public List<GymDto> findAllByMember(Member member) {
        List<MemberGym> memberGymList = memberGymRepository.findAllByMember(member);
        return memberGymList.stream()
                .map(memberGym -> gymMapper.toDto(memberGym.getGym()))
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateByMember(Member member, List<GymDto> gymDtos) {
        List<Gym> gyms = saveAndGetGyms(gymDtos);
        List<MemberGym> memberGyms = memberGymRepository.findAllByMember(member);
        for (MemberGym memberGym : memberGyms) {
            Gym gym = memberGym.getGym();
            if (gyms.contains(gym)) {
                gyms.remove(gym);
                continue;
            }
            memberGym.delete();
        }
        for (Gym gym : gyms) {
            memberGymRepository.save(MemberGym.builder()
                    .member(member)
                    .gym(gym)
                    .build());
        }
    }

    private List<Gym> saveAndGetGyms(List<GymDto> gymDtos) {
        List<Gym> gyms = new ArrayList<>();
        for (GymDto gymDto : gymDtos) {
            gyms.add(saveGym(gymDto));
        }
        return gyms;
    }

    private Gym saveGym(GymDto gymDto) {
        Gym gym = gymRepository.findByGymDto(gymDto);
        if (gym == null) {
            gym = gymRepository.save(gymMapper.toEntity(gymDto));
        }
        return gym;
    }
}
