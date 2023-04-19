package com.lastone.core.repository.recruitment;

import com.lastone.core.dto.recruitment.RecruitmentDetailDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import org.springframework.data.domain.Slice;

public interface RecruitmentRepositoryCustom {

    Slice<RecruitmentListDto> getListDto(RecruitmentSearchCondition searchCondition);
    RecruitmentDetailDto getDetailDto(Long recruitmentId);
}
