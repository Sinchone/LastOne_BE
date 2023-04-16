package com.lastone.core.repository.recruitment;

import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.domain.recruitment.WorkoutPart;
import com.lastone.core.dto.recruitment.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;
import java.time.LocalDateTime;
import java.util.List;
import static com.lastone.core.domain.recruitment.QRecruitment.recruitment;
import static com.lastone.core.domain.recruitment_img.QRecruitmentImg.recruitmentImg;

@RequiredArgsConstructor
public class RecruitmentRepositoryImpl implements RecruitmentRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public RecruitmentDetailDto getDetailDto(Long recruitmentId) {
        RecruitmentDetailDto recruitmentDetailDto = queryFactory
                .select(new QRecruitmentDetailDto(
                        recruitment.member.id,
                        recruitment.member.nickname,
                        recruitment.member.profileUrl,
                        recruitment.member.workoutPurpose,
                        recruitment.gym,
                        recruitment.id,
                        recruitment.title,
                        recruitment.description,
                        recruitment.preferGender,
                        recruitment.createdAt,
                        recruitment.startedAt))
                .from(recruitment)
                .where(recruitment.id.eq(recruitmentId))
                .fetchOne();

        if (ObjectUtils.isEmpty(recruitmentDetailDto)) {
            return null;
        }

        List<String> imgUrls = queryFactory.select(recruitmentImg.imgUrl)
                .from(recruitmentImg)
                .where(recruitmentImg.recruitment.id.eq(recruitmentId))
                .fetch();

        recruitmentDetailDto.setImgUrls(imgUrls);
        return recruitmentDetailDto;
    }

    @Override
    public Page<RecruitmentListDto> getListDto(Pageable pageable, RecruitmentSearchCondition searchCondition) {

        List<RecruitmentListDto> content = queryFactory
                .select(new QRecruitmentListDto(
                        recruitment.id,
                        recruitment.title,
                        recruitment.gym.name,
                        recruitment.startedAt,
                        recruitment.recruitmentStatus,
                        recruitment.preferGender,
                        recruitment.workoutPart
                ))
                .from(recruitment)
                .where(
                        eqWorkoutPart(searchCondition.getWorkoutPart()),
                        eqPreferGender(searchCondition.getPreferGender()),
                        isRecruitingOrNot(searchCondition.getIsRecruiting()),
                        eqStartedAt(searchCondition.getLocalDateTime()),
                        eqGymName(searchCondition.getGymName()),
                        isMatchingTitleOrGymName(searchCondition.getTitle())
                )
                .orderBy(sortList(searchCondition.getIsNewest()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    private OrderSpecifier<?> sortList(Boolean newest) {
        if (!newest) {
            return recruitment.startedAt.asc();
        }
        return recruitment.createdAt.desc();
    }

    private BooleanExpression eqWorkoutPart(WorkoutPart workoutPart) {
        if (ObjectUtils.isEmpty(workoutPart)) {
            return null;
        }
        return recruitment.workoutPart.eq(workoutPart);
    }

    private BooleanExpression eqPreferGender(PreferGender preferGender) {
        if (ObjectUtils.isEmpty(preferGender)) {
            return null;
        }
        return recruitment.preferGender.eq(preferGender);
    }

    private BooleanExpression isRecruitingOrNot(Boolean isRecruiting) {
        if (!isRecruiting) {
            return null;
        }
        return recruitment.recruitmentStatus.eq(RecruitmentStatus.RECRUITING)
                .and(recruitment.startedAt.goe(LocalDateTime.now()));
    }

    private BooleanExpression eqStartedAt(LocalDateTime time) {
        if (ObjectUtils.isEmpty(time)) {
            return null;
        }
        return recruitment.startedAt.year().eq(time.getYear())
                .and(recruitment.startedAt.month().eq(time.getMonthValue()))
                .and(recruitment.startedAt.dayOfMonth().eq(time.getDayOfMonth()));
    }

    private BooleanExpression isMatchingTitleOrGymName(String searchText) {
        if (ObjectUtils.isEmpty(searchText)) {
            return null;
        }
        return recruitment.title.contains(searchText)
                .or(recruitment.gym.name.contains(searchText));
    }

    private BooleanExpression eqGymName(String gymName) {
        if (ObjectUtils.isEmpty(gymName)) {
            return null;
        }
        return recruitment.gym.name.eq(gymName);
    }
}