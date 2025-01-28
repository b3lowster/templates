package com.jwtexample.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
}
