package com.lastone.core.service.member;

import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.member.MemberDto;
import com.lastone.core.dto.member.MemberUpdateDto;
import com.lastone.core.mapper.mapper.MemberMapper;
import com.lastone.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;


//    public MyPageDto findMyPageInfoByMemberId(Long memberId) {
//
//        MyPageDto myPageDto = new MyPageDto();
//
//        Member member = memberRepository.findById(memberId).orElseThrow(NullPointerException::new);
//        MemberDto memberDto = memberMapper.toDto(member);
//        myPageDto.setMember(memberDto);
//
//        List<MemberGym> memberGyms = memberGymRepository.findAllByMemberId(memberId);
//        if (memberGyms.size() != 0) {
//            for (MemberGym memberGym : memberGyms) {
//                Long gymId = memberGym.getGymId();
//                Gym gym = gymRepository.findById(gymId).orElseThrow(NullPointerException::new);
//                log.info("gymEntity ={}", gym);
//                GymDto gymDto = gymMapper.toDto(gym);
//                myPageDto.registerGym(gymDto);
//            }
//        }
//
//        Sbd sbd = sbdRepository.findLatestRecordByMemberId(memberId);
//        SbdDto sbdDto = sbdMapper.toDto(sbd);
//        myPageDto.setSbd(sbdDto);
//
//        return myPageDto;
//    }
//
//    public void checkIdentity(Long memberId, Long memberDtoId) {
//        if (!memberId.equals(memberDtoId)) {
//            throw new IllegalArgumentException("업데이트 권한이 없습니다.");
//        }
//    }
//
//    public void updateMyPage(MyPageUpdateDto myPageUpdateDto) {
//        updateMember(myPageUpdateDto);
//        updateGym(myPageUpdateDto);
//    }
//
//    private void updateGym(MyPageUpdateDto myPageUpdateDto) {
//        List<MemberGym> memberGyms = memberGymRepository.findAllByMemberId(myPageUpdateDto.getMemberId());
//        GymDto gym1 = new GymDto(myPageUpdateDto.getGym1Name(), myPageUpdateDto.getGym1location(), myPageUpdateDto.getGym1latitude(), myPageUpdateDto.getGym1longitude());
//        GymDto gym2 = new GymDto(myPageUpdateDto.getGym2Name(), myPageUpdateDto.getGym2location(), myPageUpdateDto.getGym2latitude(), myPageUpdateDto.getGym2longitude());
//
//        for (MemberGym memberGym : memberGyms) {
//            Gym gym = gymRepository.findById(memberGym.getGymId()).orElse(null);
//            Gym gym1 = gymRepository.findByGymDto(gym1);
//            if (gym1 == null && StringUtils.hasText(myPageUpdateDto.getGym1Name())) {
//                gymRepository.save(Gym.builder()
//                        .name())
//
//            }
//
//            if (isEqualToEachOther(gym, myPageUpdateDto)) {
//                continue;
//            }
//
//
//
//        }
//    }
//
//    private boolean isEqualToEachOther(Gym gym, MyPageUpdateDto myPageUpdateDto) {
//
//        if (isEqualToGym1(gym, myPageUpdateDto)) {
//            return true;
//        }
//        if (isEqualToGym2(gym, myPageUpdateDto)) {
//            return true;
//        }
//        return false;
//    }
//
//    private boolean isEqualToGym1(Gym gym, MyPageUpdateDto myPageUpdateDto) {
//        if (!gym.getName().equals(myPageUpdateDto.getGym1Name())) {
//            return false;
//        }
//        if (!gym.getLocation().equals(myPageUpdateDto.getGym1location())) {
//            return false;
//        }
//        if (gym.getLatitude().equals(myPageUpdateDto.getGym1latitude())) {
//            return false;
//        }
//        if (gym.getLongitude().equals(myPageUpdateDto.getGym1longitude())) {
//            return false;
//        }
//        return true;
//    }
//
//    private boolean isEqualToGym2(Gym gym, MyPageUpdateDto myPageUpdateDto) {
//        if (!gym.getName().equals(myPageUpdateDto.getGym2Name())) {
//            return false;
//        }
//        if (!gym.getLocation().equals(myPageUpdateDto.getGym2location())) {
//            return false;
//        }
//        if (gym.getLatitude().equals(myPageUpdateDto.getGym2latitude())) {
//            return false;
//        }
//        if (gym.getLongitude().equals(myPageUpdateDto.getGym2longitude())) {
//            return false;
//        }
//        return true;
//    }
//
//
//    private void updateMember(MyPageUpdateDto myPageUpdateDto) {
//        Member member = memberRepository.findById(myPageUpdateDto.getMemberId()).orElseThrow(NullPointerException::new);
//        MemberDto memberDto = MemberDto.builder()
//                .nickname(myPageUpdateDto.getNickname())
//                .gender(myPageUpdateDto.getGender())
//                .workoutDay(myPageUpdateDto.getWorkoutDay())
//                .workoutTime(myPageUpdateDto.getWorkoutTime())
//                .workoutPurpose(myPageUpdateDto.getWorkoutPurpose())
//                .build();
//        member.updateMember(memberDto);
//    }

    public MemberDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NullPointerException::new);
        return memberMapper.toDto(member);
    }

    public void update(MemberUpdateDto memberUpdateDto, MultipartFile profileImg) {
        Member member = memberRepository.findById(memberUpdateDto.getId()).orElseThrow(NullPointerException::new);
        // todo 이미지 파일 처리
        member.update(memberUpdateDto);

    }

    public void isExist(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            throw new NullPointerException();
        }
    }
}
