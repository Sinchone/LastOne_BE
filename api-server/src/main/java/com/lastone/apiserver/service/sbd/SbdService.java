package com.lastone.apiserver.service.sbd;

import com.lastone.core.dto.sbd.SbdDto;

public interface SbdService {

    public SbdDto findByMemberId(Long memberId);

    public void updateByMemberId(SbdDto sbdDto, Long memberId);
}
