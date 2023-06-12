package com.lastone.core.repository.recruitment;

import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.dto.recruitment.RecruitmentDetailDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import org.springframework.data.domain.Slice;
import java.util.List;
import java.util.Optional;

public interface RecruitmentRepositoryCustom {

    Slice<RecruitmentListDto> getListDto(RecruitmentSearchCondition searchCondition);

    Optional<RecruitmentDetailDto> getDetailDto(Long recruitmentId);

    List<RecruitmentListDto> getListDtoInMainPage();

    Recruitment findOneCompleteStatusAtTodayByMemberId(Long memberId);

    List<Recruitment> findAllCompleteStatusBeforeToday(Long memberId);
}
