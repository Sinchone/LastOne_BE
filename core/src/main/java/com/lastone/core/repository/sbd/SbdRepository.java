package com.lastone.core.repository.sbd;

import com.lastone.core.domain.sbd.Sbd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SbdRepository extends JpaRepository<Sbd, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM sbd s WHERE s.member_id = :memberId ORDER BY s.sbd_id DESC LIMIT 1")
    Sbd findLatestRecordByMemberId(@Param("memberId") Long memberId);


}
