package com.lastone.core.repository.recruitment;

import com.lastone.core.dto.recruitment.RecruitmentDetailDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitmentRepositoryCustom {

    Page<RecruitmentListDto> getListDto(Pageable pageable, RecruitmentSearchCondition searchCondition);
    RecruitmentDetailDto getDetailDto(Long recruitmentId);
}
