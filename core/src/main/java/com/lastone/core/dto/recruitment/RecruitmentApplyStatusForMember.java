package com.lastone.core.dto.recruitment;

import lombok.Getter;

@Getter
public class RecruitmentApplyStatusForMember {

    Boolean isApply;

    public RecruitmentApplyStatusForMember(boolean isApply) {
        this.isApply = isApply;
    }
}
