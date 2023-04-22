package com.lastone.core.repository.applyment;

import com.lastone.core.domain.applyment.Applyment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplymentRepository extends JpaRepository<Applyment, Long> {
}
