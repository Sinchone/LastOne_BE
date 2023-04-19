package com.lastone.core.repository.recruitment;

import com.lastone.core.domain.recruitment.PreferGender;
import com.lastone.core.domain.recruitment.Recruitment;
import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.domain.recruitment.WorkoutPart;
import com.lastone.core.dto.recruitment.QRecruitmentDetailDto;
import com.lastone.core.dto.recruitment.RecruitmentDetailDto;
import com.lastone.core.dto.recruitment.RecruitmentListDto;
import com.lastone.core.dto.recruitment.RecruitmentSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.util.ObjectUtils;
import java.time.LocalDateTime;
import java.util.List;
import static com.lastone.core.domain.gym.QGym.gym;
import static com.lastone.core.domain.member.QMember.member;
import static com.lastone.core.domain.recruitment.QRecruitment.recruitment;
import static com.lastone.core.domain.recruitment_img.QRecruitmentImg.recruitmentImg;

@RequiredArgsConstructor
public class RecruitmentRepositoryImpl implements RecruitmentRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_SIZE = 6;

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
                .where(
                        recruitment.id.eq(recruitmentId),
                        recruitment.isDeleted.eq(false)
                )
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
    public Slice<RecruitmentListDto> getListDto(RecruitmentSearchCondition searchCondition) {

        Pageable pageable = PageRequest.of(DEFAULT_OFFSET, DEFAULT_SIZE);

        List<Recruitment> content = queryFactory
                .selectFrom(recruitment)
                .leftJoin(recruitment.member, member).fetchJoin()
                .leftJoin(recruitment.gym, gym).fetchJoin()
                .leftJoin(recruitment.recruitmentImgs, recruitmentImg).fetchJoin()
                .where(
                        eqWorkoutPart(searchCondition.getWorkoutPart()),
                        eqPreferGender(searchCondition.getPreferGender()),
                        isRecruitingOrNot(searchCondition.getIsRecruiting()),
                        eqStartedAt(searchCondition.getLocalDateTime()),
                        eqGymName(searchCondition.getGymName()),
                        isMatchingTitleOrGymName(searchCondition.getTitle()),
                        ltLastId(searchCondition.getLastId()),
                        recruitment.isDeleted.eq(false)
                )
                .orderBy(recruitment.createdAt.desc())
                .limit(DEFAULT_SIZE + 1)
                .fetch();

        return checkLastPage(pageable, content);
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

    private BooleanExpression ltLastId(Long lastId) {
        if (lastId == null) {
            return null;
        }
        return recruitment.id.lt(lastId);
    }

    private Slice<RecruitmentListDto> checkLastPage(Pageable pageable, List<Recruitment> recruitments) {
        boolean hasNext = false;
        if (recruitments.size() > DEFAULT_SIZE) {
            hasNext = true;
            recruitments.remove(DEFAULT_SIZE);
        }
        List<RecruitmentListDto> content = RecruitmentListDto.toDto(recruitments);
        return new SliceImpl<>(content, pageable, hasNext);
    }
}