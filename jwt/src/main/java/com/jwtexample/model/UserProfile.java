package com.jwtexample.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class UserProfile {
    private Long id;
    private String userName;
}
