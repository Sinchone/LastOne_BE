package com.lastone.core.dto.recruitment;

import lombok.Getter;

@Getter
public class RecruitmentIdDto {

    private final Long id;

    public RecruitmentIdDto(Long id) {
        this.id = id;
    }
}