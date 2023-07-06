package com.lastone.core.dto.recruitment;

import lombok.Getter;

@Getter
public class RecruitmentApplyStatusForMember {

    private final Boolean isApply;

    private final Long applicationId;


    public RecruitmentApplyStatusForMember(boolean isApply) {
        this.isApply = isApply;
        this.applicationId = null;
    }

    public RecruitmentApplyStatusForMember(boolean isApply, Long applicationId) {
        this.isApply = isApply;
        this.applicationId = applicationId;
    }
}
