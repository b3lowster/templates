package com.jwtexample.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String username;
}
