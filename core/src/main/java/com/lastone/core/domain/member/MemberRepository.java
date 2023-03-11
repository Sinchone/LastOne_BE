package com.lastone.core.domain.member;

import com.lastone.core.domain.gym.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
}
