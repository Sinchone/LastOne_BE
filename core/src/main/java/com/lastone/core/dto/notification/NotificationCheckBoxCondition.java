package com.lastone.core.dto.notification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificationCheckBoxCondition {

    private Boolean matchingSuccessOnly;

    private Boolean matchingCancelOnly;

    private Boolean matchingRequestOnly;
}