package com.lastone.core.repository.recruitment;

import com.lastone.core.domain.recruitment.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long>, RecruitmentRepositoryCustom {

    @Query("select r from Recruitment r where r.id = :id and r.isDeleted = false")
    Optional<Recruitment> findByIdAndDeletedIsFalse(Long id);

    @Modifying
    @Query("update Recruitment r set r.recruitmentStatus = 'COMPLETE' where r.id = :id")
    int updateStatusToComplete(Long id);
}
