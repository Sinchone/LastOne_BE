package com.lastone.apiserver.service.sbd;

import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.sbd.SbdDto;

public interface SbdService {

    SbdDto findByMember(Member member);

    void updateByMember(Member member, SbdDto sbdDto);
}
