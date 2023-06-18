package com.lastone.apiserver.service.sbd;

import com.lastone.core.domain.member.Member;
import com.lastone.core.domain.sbd.Sbd;
import com.lastone.core.dto.sbd.SbdDto;
import com.lastone.core.util.mapper.SbdMapper;
import com.lastone.core.repository.sbd.SbdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SbdServiceImpl implements SbdService {

    private final SbdRepository sbdRepository;

    private final SbdMapper sbdMapper;

    public SbdDto findByMember(Member member) {
        Sbd sbd = sbdRepository.findLatestRecordByMemberId(member.getId());
        return sbdMapper.toDto(sbd);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateByMember(Member member, SbdDto sbdDto) {
        Sbd findSbd = sbdRepository.findLatestRecordByMemberId(member.getId());
        Sbd sbd = sbdMapper.toEntity(sbdDto);
        if (sbd == null) {
            sbd = Sbd.builder()
                    .benchPress(0)
                    .deadLift(0)
                    .squat(0)
                    .build();
        }
        if (findSbd != null && findSbd.isEqualTo(sbd)) {
            return;
        }
        sbd.assignMember(member);
        sbdRepository.save(sbd);
    }
}
