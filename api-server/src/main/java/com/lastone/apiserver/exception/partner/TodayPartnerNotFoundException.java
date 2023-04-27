package com.lastone.apiserver.exception.partner;

import com.lastone.core.common.response.ErrorCode;

public class TodayPartnerNotFoundException extends PartnerException {
    public TodayPartnerNotFoundException() {
        super(ErrorCode.TODAY_PARTNER_NOT_FOUND);
    }
}