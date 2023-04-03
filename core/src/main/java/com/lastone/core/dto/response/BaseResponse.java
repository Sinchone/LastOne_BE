package com.lastone.core.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class BaseResponse {

    private String message;

    private Object data;

}
