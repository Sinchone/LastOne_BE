package com.lastone.core.repository.application;

import com.lastone.core.domain.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationRepositoryCustom {
}
