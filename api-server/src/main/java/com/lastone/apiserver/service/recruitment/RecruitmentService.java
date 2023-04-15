package com.lastone.apiserver.service.recruitment;

import com.lastone.core.dto.recruitment.RecruitmentCreateDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecruitmentService {

    void createRecruitment(Long memberId, RecruitmentCreateDto recruitmentCreateDto, List<MultipartFile> imgFiles) throws IOException;
    Page<RecruitmentListDto> getList(RecruitmentSearchCondition searchCondition);
}
