package com.lastone.core.repository.recruitment;

import com.lastone.core.domain.recruitment.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long>, RecruitmentRepositoryCustom {

    @Query("select r from Recruitment r where r.id = :id and r.isDeleted = false")
    Optional<Recruitment> findByIdAndDeletedIsFalse(@Param("id") Long id);
}
