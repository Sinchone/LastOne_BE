package com.lastone.apiserver.service.recruitment;

import com.lastone.core.dto.recruitment.RecruitmentRequestDto;
import com.lastone.core.dto.recruitment.RecruitmentDetailDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface RecruitmentService {

    Slice<RecruitmentListDto> getList(RecruitmentSearchCondition searchCondition);

    RecruitmentDetailDto getDetail(Long recruitmentId);

    List<RecruitmentListDto> getMainList();

    void createRecruitment(Long memberId, RecruitmentRequestDto recruitmentCreateDto, List<MultipartFile> imgFiles) throws IOException;

    void updateRecruitment(Long recruitmentId, Long memberId, RecruitmentRequestDto recruitment, List<MultipartFile> imgFiles) throws IOException;

    void deleteRecruitment(Long recruitmentId, Long id);
}
