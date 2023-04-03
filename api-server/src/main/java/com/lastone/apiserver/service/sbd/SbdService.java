package com.lastone.apiserver.service.sbd;

import com.lastone.core.dto.sbd.SbdDto;

public interface SbdService {

    SbdDto findByMemberId(Long memberId);

    void updateByMemberId(Long memberId, SbdDto sbdDto);
}
