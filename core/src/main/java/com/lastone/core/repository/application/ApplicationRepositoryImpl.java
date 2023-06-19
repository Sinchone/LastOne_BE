package com.lastone.core.repository.application;

import com.lastone.core.domain.application.Application;
import com.lastone.core.domain.application.ApplicationStatus;
import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.dto.applicaation.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static com.lastone.core.domain.application.QApplication.application;
import static com.lastone.core.domain.gym.QGym.gym;
import static com.lastone.core.domain.member.QMember.member;
import static com.lastone.core.domain.recruitment.QRecruitment.recruitment;

@RequiredArgsConstructor
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    private static final int ONE_DAY = 1;

    @Override
    public List<ApplicationReceivedDto> getReceivedList(Long memberId) {

        LocalDateTime now = LocalDateTime.now();

        List<ApplicationReceivedDto> applicationReceivedDtos = queryFactory
                .select(new QApplicationReceivedDto(
                        recruitment.id,
                        recruitment.title,
                        recruitment.startedAt,
                        gym.name))
                .from(recruitment)
                .where(
                        recruitment.member.id.eq(memberId),
                        recruitment.isDeleted.eq(false),
                        recruitment.recruitmentStatus.eq(RecruitmentStatus.RECRUITING)
                                .or(recruitment.recruitmentStatus
                                        .eq(RecruitmentStatus.COMPLETE)
                                        .and(recruitment.startedAt.lt(now.plusDays(ONE_DAY)))),
                        recruitment.startedAt.goe(now)
                )
                .leftJoin(recruitment.gym, gym)
                .orderBy(recruitment.startedAt.desc())
                .fetch();

        List<ApplicationReceivedDto> removeList = new ArrayList<>();
        for (ApplicationReceivedDto applicationReceivedDto : applicationReceivedDtos) {
            Long recruitmentId = applicationReceivedDto.getId();
            List<ApplicationDto> applicantions = queryFactory
                    .select(new QApplicationDto(
                            application.id,
                            application.applicant.id,
                            application.applicant.nickname,
                            application.applicant.profileUrl,
                            application.applicant.gender,
                            application.status,
                            application.createdAt))
                    .from(application)
                    .where(application.recruitment.id.eq(recruitmentId))
                    .leftJoin(application.applicant, member)
                    .orderBy(application.createdAt.desc())
                    .fetch();
            if (applicantions.isEmpty()) {
                removeList.add(applicationReceivedDto);
            }
            applicationReceivedDto.includeApplicants(applicantions);
        }
        applicationReceivedDtos.removeAll(removeList);
        return applicationReceivedDtos;
    }

    @Override
    public List<ApplicationRequestedDto> getRequestedList(Long memberId) {

        LocalDateTime now = LocalDateTime.now();

        return queryFactory
                .select(new QApplicationRequestedDto(
                        application.id,
                        application.recruitment.id,
                        application.recruitment.title,
                        application.recruitment.gym.name,
                        application.recruitment.startedAt,
                        application.recruitment.member.id,
                        application.recruitment.member.profileUrl,
                        application.recruitment.member.nickname,
                        application.recruitment.member.gender,
                        application.status,
                        application.createdAt))
                .from(application)
                .where(
                        application.applicant.id.eq(memberId),
                        application.status.eq(ApplicationStatus.WAITING)
                                .or(application.status.eq(ApplicationStatus.SUCCESS)
                                        .and(application.recruitment.startedAt.loe(now.plusDays(ONE_DAY)))),
                        application.recruitment.startedAt.goe(now))
                .orderBy(application.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Application> findAllByRecruitmentId(Long recruitmentId) {
        return queryFactory
                .select(application)
                .from(application)
                .where(application.recruitment.id.eq(recruitmentId))
                .fetch();
    }

    @Override
    public void updateStatus(Long recruitmentId, Long successApplicationId) {

        queryFactory
                .update(application)
                .set(application.status, ApplicationStatus.SUCCESS)
                .where(
                        application.id.eq(successApplicationId),
                        application.recruitment.id.eq(recruitmentId))
                .execute();

        queryFactory
                .update(application)
                .set(application.status, ApplicationStatus.FAILURE)
                .where(
                        application.recruitment.id.eq(recruitmentId),
                        application.id.ne(successApplicationId),
                        application.status.ne(ApplicationStatus.CANCEL))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public Application findMyTodaySuccessApplication(Long memberId) {

        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime tomorrow = today.plusDays(1);

        return queryFactory
                .selectFrom(application)
                .where(
                        application.applicant.id.eq(memberId),
                        application.status.eq(ApplicationStatus.SUCCESS),
                        application.recruitment.startedAt.between(today, tomorrow))
                .leftJoin(application.recruitment, recruitment).fetchJoin()
                .leftJoin(recruitment.gym, gym).fetchJoin()
                .leftJoin(recruitment.member, member).fetchJoin()
                .fetchFirst();
    }

    @Override
    public List<Application> findAllSuccessStatusBeforeToday(Long memberId) {

        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

        return queryFactory
                .selectFrom(application)
                .where(
                        application.applicant.id.eq(memberId),
                        application.status.eq(ApplicationStatus.SUCCESS),
                        application.recruitment.startedAt.loe(today))
                .leftJoin(application.recruitment, recruitment).fetchJoin()
                .leftJoin(recruitment.member, member).fetchJoin()
                .orderBy(application.recruitment.startedAt.asc())
                .fetch();
    }
}