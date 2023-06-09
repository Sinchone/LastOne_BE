package com.lastone.core.repository.member_gym;

import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.member_gym.MemberGym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MemberGymRepository extends JpaRepository<MemberGym, Long> {

    @Query("select m from MemberGym m where m.member = :member and m.isDeleted = false")
    List<MemberGym> findAllByMember(@Param("member") Member member);
}
