package com.lastone.apiserver.service.partner;

import com.lastone.core.dto.partner.TodayPartnerDto;

public interface PartnerService {
    TodayPartnerDto findTodayPartner(Long memberId);
}
