package com.lastone.core.service.member;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.sbd.Sbd;
import com.lastone.core.dto.gym.GymDto;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.mypage.MyPageDto;
import com.lastone.core.dto.sbd.SbdDto;
import com.lastone.core.mapper.mapper.SbdMapper;
import com.lastone.core.repository.gym.GymRepository;
import com.lastone.core.domain.member_gym.MemberGym;
import com.lastone.core.repository.member_gym.MemberGymRepository;
import com.lastone.core.mapper.mapper.GymMapper;
import com.lastone.core.mapper.mapper.MemberMapper;
import com.lastone.core.repository.member.MemberRepository;
import com.lastone.core.repository.sbd.SbdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberGymRepository memberGymRepository;

    private final GymRepository gymRepository;

    private final SbdRepository sbdRepository;

    private final MemberMapper memberMapper;

    private final SbdMapper sbdMapper;

    private final GymMapper gymMapper;

    public MyPageDto findMyPageInfoByMemberId(Long memberId) {

        MyPageDto myPageDto = new MyPageDto();

        Member member = memberRepository.findById(memberId).orElseThrow(NullPointerException::new);
        MemberDto memberDto = memberMapper.toDto(member);
        myPageDto.setMember(memberDto);

        List<MemberGym> memberGyms = memberGymRepository.findAllByMemberId(memberId);
        if (memberGyms.size() != 0) {
            for (MemberGym memberGym : memberGyms) {
                Long gymId = memberGym.getGymId();
                Gym gym = gymRepository.findById(gymId).orElseThrow(NullPointerException::new);
                log.info("gymEntity ={}", gym);
                GymDto gymDto = gymMapper.toDto(gym);
                myPageDto.registerGym(gymDto);
            }
        }

        Sbd sbd = sbdRepository.findLatestRecordByMemberId(memberId);
        SbdDto sbdDto = sbdMapper.toDto(sbd);
        myPageDto.setSbd(sbdDto);

        return myPageDto;
    }

    public void checkIdentity(Long memberId, Long memberDtoId) {
        if (!memberId.equals(memberDtoId)) {
            throw new IllegalArgumentException("업데이트 권한이 없습니다.");
        }
    }


//    public void updateMyPage(MemberDto memberDto) {
//        List<GymDto> gyms = Arrays.asList(memberDto.getGym1(), memberDto.getGym2());
//        validateAndSaveGyms(gyms, memberDto);
//    }
//
//    private void validateAndSaveGyms(List<GymDto> gyms, MemberDto memberDto) {
//        for (GymDto gym : gyms) {
//            Long gymId = gym.getId();
//            if (gymId != null) {
//                continue;
//            }
//            gymService.isAlreadyExist(gym);
//        }
//    }
}
