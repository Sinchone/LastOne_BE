package com.lastone.apiserver.exception.partner;

import com.lastone.core.common.response.ErrorCode;

public class PartnerHistoryNotFoundException extends PartnerException {
    public PartnerHistoryNotFoundException() {
        super(ErrorCode.PARTNER_HISTORY_NOT_FOUND);
    }
}