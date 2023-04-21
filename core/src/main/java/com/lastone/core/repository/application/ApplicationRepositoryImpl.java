package com.lastone.core.repository.application;

import com.lastone.core.domain.recruitment.RecruitmentStatus;
import com.lastone.core.dto.applicaation.ApplicantDto;
import com.lastone.core.dto.applicaation.ApplicationReceivedDto;
import com.lastone.core.dto.applicaation.QApplicantDto;
import com.lastone.core.dto.applicaation.QApplicationReceivedDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
            List<ApplicantDto> applicants = queryFactory
                    .select(new QApplicantDto(
                            member.id,
                            member.nickname,
                            member.profileUrl,
                            member.gender,
                            application.createdAt))
                    .from(application)
                    .where(application.recruitment.id.eq(recruitmentId))
                    .leftJoin(application.applicant, member)
                    .orderBy(application.createdAt.desc())
                    .fetch();
            if (applicants.isEmpty()) {
                removeList.add(applicationReceivedDto);
            }
            applicationReceivedDto.includeApplicants(applicants);
        }
        applicationReceivedDtos.removeAll(removeList);
        return applicationReceivedDtos;
    }
}