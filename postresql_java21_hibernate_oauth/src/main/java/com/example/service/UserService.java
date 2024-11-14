package com.example.service;

import com.example.model.db.UserProfile;
import com.example.model.dto.UserProfileDto;

import java.util.Optional;

public interface UserService {
    UserProfileDto getUserProfileDtoById(Long userId);
    UserProfile getUserProfileById(Long userId);
    Optional<UserProfileDto> getUserProfileByEmail(String email);
    UserProfileDto registerNewUserProfile(UserProfile userProfile);
    UserProfileDto updateNewUserProfile(UserProfileDto updateUserRequest);
}
