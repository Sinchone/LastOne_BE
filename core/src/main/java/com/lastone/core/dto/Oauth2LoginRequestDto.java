package com.lastone.core.dto;

import lombok.Getter;

@Getter
public class Oauth2LoginRequestDto {
    private String code;
    private String env;
}