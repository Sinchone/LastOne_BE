package com.lastone.apiserver;

import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import com.lastone.core.repository.recruitment.RecruitmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("api-local")
@SpringBootTest
@Transactional
public class RecruitmentQueryTest {

    @Autowired
    private RecruitmentRepository recruitmentRepository;
    @Test
    void test1() {
        RecruitmentSearchCondition searchCondition = new RecruitmentSearchCondition(
                "BACK", "남성", null, "02d36",
                null, null, null);
        System.out.println(searchCondition.getLocalDateTime());
        Slice<RecruitmentListDto> listDto = recruitmentRepository.getListDto(searchCondition);
    }

    @Test
    void test2() {
        RecruitmentSearchCondition searchCondition = new RecruitmentSearchCondition(
                "BACK", "남성", null, null,
                null, null, null);

        Slice<RecruitmentListDto> listDto = recruitmentRepository.getListDto(searchCondition);
        System.out.println(listDto.getSize());
    }

    @Test
    void test3() {
        RecruitmentSearchCondition searchCondition = new RecruitmentSearchCondition(
                null, null, null, null,
                null, null, null);

        Slice<RecruitmentListDto> listDto = recruitmentRepository.getListDto(searchCondition);
        List<RecruitmentListDto> content = listDto.getContent();
        for (RecruitmentListDto recruitmentListDto : content) {
            System.out.println(recruitmentListDto.toString());
        }
    }
}