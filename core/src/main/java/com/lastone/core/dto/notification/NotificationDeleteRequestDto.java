package com.lastone.core.dto.notification;

import lombok.Getter;
import java.util.List;

@Getter
public class NotificationDeleteRequestDto {

    private List<Long> deleteIdList;
}