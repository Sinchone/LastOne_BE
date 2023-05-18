package com.lastone.apiserver.service.partner;

import com.lastone.core.dto.partner.PartnerHistoryDto;
import com.lastone.core.dto.partner.TodayPartnerDto;
import java.util.List;

public interface PartnerService {

    TodayPartnerDto findTodayPartner(Long memberId);

    List<PartnerHistoryDto> findPartnerHistoryList(Long memberId);
}
