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
    public void updateByMember(Member member, List<GymDto> gymDtoList) {
        List<Gym> gymList = saveGyms(gymDtoList);
        List<MemberGym> memberGymList = memberGymRepository.findAllByMember(member);
        updateMemberGym(gymList, memberGymList, member);
    }

    private void updateMemberGym(List<Gym> gymList, List<MemberGym> memberGymList, Member member) {
        memberGymList.stream()
                .filter(memberGym -> isNotExistInGymList(gymList, memberGym.getGym()))
                .forEach(MemberGym::delete);

        gymList.forEach(gym -> memberGymRepository.save(MemberGym.builder()
                .member(member)
                .gym(gym)
                .build()));
    }

    private boolean isNotExistInGymList(List<Gym> gymList, Gym gym) {
        return !gymList.contains(gym);
    }

    private List<Gym> saveGyms(List<GymDto> gymDtoList) {
        List<GymDto> unSavedGyms = filterUnSavedGyms(gymDtoList);
        unSavedGyms.forEach(gymDto -> gymRepository.save(gymMapper.toEntity(gymDto)));
        return gymDtoList.stream().map(gymRepository::findByGymDto).collect(Collectors.toList());
    }

    private List<GymDto> filterUnSavedGyms(List<GymDto> gymDtoList) {
        return gymDtoList.stream().filter(this::isNotExistInRepository).collect(Collectors.toList());
    }

    private boolean isNotExistInRepository(GymDto gymDto) {
        Gym gym = gymRepository.findByGymDto(gymDto);
        return gym == null;
    }
}
