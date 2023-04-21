package com.lastone.core.repository.application;

import com.lastone.core.dto.applicaation.ApplicationReceivedDto;

import java.util.List;

public interface ApplicationRepositoryCustom {

    List<ApplicationReceivedDto> getReceivedList(Long memberId);
}