package com.lastone.apiserver.query;

import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.dto.recruitment.RecruitmentDetailDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ActiveProfiles("api-local")
@SpringBootTest
@Transactional
public class RecruitmentQueryTest {

    @Autowired
    private RecruitmentRepository recruitmentRepository;
    @Test
    @DisplayName("아무 조건이 없을 경우 모집글 리스트 검색 성능 테스트")
    void recruitmentListWithNoConditionTest() {
        RecruitmentSearchCondition searchCondition = new RecruitmentSearchCondition(
                null, null, null, null,
                null, null, null);

        Slice<RecruitmentListDto> listDto = recruitmentRepository.getListDto(searchCondition);
        List<RecruitmentListDto> content = listDto.getContent();
        for (RecruitmentListDto recruitmentListDto : content) {
            System.out.println(recruitmentListDto.toString());
        }
    }

    @Test
    @DisplayName("조건이 있을 경우 모집글 리스트 검색 성능 테스트")
    void recruitmentListWithConditionTest() {
        RecruitmentSearchCondition searchCondition = new RecruitmentSearchCondition(
                null, null, null, "cc",
                "ovpkfvnefpjazwj", true, null);
        Slice<RecruitmentListDto> listDto = recruitmentRepository.getListDto(searchCondition);
    }

    @Test
    @DisplayName("모집글 상세 쿼리 성능 테스트")
    void recruitmentDetailTest() {
        RecruitmentDetailDto detailDto = recruitmentRepository.getDetailDto(500000L);
        List<String> imgUrls = detailDto.getImgUrls();
        for (String imgUrl : imgUrls) {
            System.out.print(imgUrl + " ");
        }
    }

    @Test
    @DisplayName("메인페이지 모집글 리스트 쿼리 성능 테스트")
    void recruitmentListInMainPageTest() {
        List<RecruitmentListDto> listDtoInMainPage = recruitmentRepository.getListDtoInMainPage();
    }

    @Test
    @DisplayName("모집글 작성 테스트")
    @Rollback(value = false)
    @Transactional
    void createRecruitmentTest() {
        Recruitment recruitment = Recruitment.builder()
                .startedAt(LocalDateTime.now())
                .build();
        recruitmentRepository.save(recruitment);
    }
}