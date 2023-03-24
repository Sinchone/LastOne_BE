package com.lastone.core.service.sbd;

import com.lastone.core.domain.sbd.Sbd;
import com.lastone.core.dto.sbd.SbdDto;
import com.lastone.core.mapper.mapper.SbdMapper;
import com.lastone.core.repository.sbd.SbdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SbdService {

    private final SbdRepository sbdRepository;

    private final SbdMapper sbdMapper;

    public SbdDto findByMemberId(Long memberId) {
        Sbd sbd = sbdRepository.findLatestRecordByMemberId(memberId);
        return sbdMapper.toDto(sbd);
    }

    public void updateByMemberId(SbdDto sbdDto, Long memberId) {
        Sbd findSbd = sbdRepository.findLatestRecordByMemberId(memberId);
        Sbd sbd = sbdMapper.toEntity(sbdDto);
        if (!sbd.isEqualTo(findSbd)) {
            sbdRepository.save(sbd);
        }
    }
}
