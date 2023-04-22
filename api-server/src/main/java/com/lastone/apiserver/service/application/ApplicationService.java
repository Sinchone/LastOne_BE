package com.lastone.apiserver.service.application;

import com.lastone.core.dto.applicaation.ApplicationReceivedDto;
import com.lastone.core.dto.applicaation.ApplicationRequestedDto;
import java.util.List;

public interface ApplicationService {

    void createApplication(Long applicantId, Long recruitmentId);

    List<ApplicationReceivedDto> getReceivedListByMemberId(Long memberId);

    List<ApplicationRequestedDto> getRequestedListByMemberId(Long memberId);
}
