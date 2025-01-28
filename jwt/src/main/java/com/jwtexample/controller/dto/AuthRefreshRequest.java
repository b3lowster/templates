package com.jwtexample.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthRefreshRequest {
    @NotEmpty
    private String refreshToken;
}
