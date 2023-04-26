package com.lastone.core.repository.application;

import com.lastone.core.domain.application.Application;
import com.lastone.core.dto.applicaation.ApplicationReceivedDto;
import com.lastone.core.dto.applicaation.ApplicationRequestedDto;
import java.util.List;

public interface ApplicationRepositoryCustom {

    List<ApplicationReceivedDto> getReceivedList(Long memberId);

    List<ApplicationRequestedDto> getRequestedList(Long memberId);

    List<Application> findAllByRecruitmentId(Long recruitmentId);

    void updateStatus(Long recruitmentId, Long applicationId);
}