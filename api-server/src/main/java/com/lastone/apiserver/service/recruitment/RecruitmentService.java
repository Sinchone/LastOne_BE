package com.lastone.apiserver.service.recruitment;

import com.lastone.core.dto.recruitment.*;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface RecruitmentService {

    Slice<RecruitmentListDto> getList(RecruitmentSearchCondition searchCondition);

    RecruitmentDetailDto getDetail(Long recruitmentId);

    List<RecruitmentListDto> getMainList();

    Long createRecruitment(Long memberId, RecruitmentRequestDto recruitmentCreateDto, List<MultipartFile> imgFiles) throws IOException;

    void updateRecruitment(Long recruitmentId, Long memberId, RecruitmentRequestDto recruitment, List<MultipartFile> imgFiles) throws IOException;

    void deleteRecruitment(Long recruitmentId, Long id);

    RecruitmentApplyStatusForMember isAlreadyApplyRecruitment(Long recruitmentId, Long memberId);
}
