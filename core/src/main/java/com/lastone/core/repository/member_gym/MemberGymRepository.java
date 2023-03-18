package com.lastone.core.repository.member_gym;

import com.lastone.core.domain.gym.Gym;
import com.lastone.core.domain.member_gym.MemberGym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface MemberGymRepository extends JpaRepository<MemberGym, Long> {

    List<MemberGym> findAllByMemberId(Long memberId);
}
