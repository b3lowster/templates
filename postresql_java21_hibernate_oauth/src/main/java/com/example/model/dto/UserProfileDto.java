package com.example.model.dto;

import com.example.model.types.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {
    private Long id;
    private String email;
    private UserRole role;
    private String userName;
    private String firstName;
    private String lastName;
    private String defaultPictureUrl;
}
