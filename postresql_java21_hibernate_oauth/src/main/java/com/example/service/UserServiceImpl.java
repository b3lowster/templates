package com.example.service;

import com.example.model.db.UserProfile;
import com.example.model.dto.UserProfileDto;
import com.example.model.exception.BadRequestException;
import com.example.model.exception.UserNotFoundException;
import com.example.model.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfileDto getUserProfileDtoById(Long userId) {
        Assert.notNull(userId, "User id cannot be null");
        log.info("Getting user details by id: {}", userId);
        return userProfileRepository.findById(userId)
                .map(UserServiceImpl::convertUserProfileDto)
                .orElseThrow(() -> new UserNotFoundException(String.format("Cannot find user by id: %s", userId)));
    }

    @Override
    public UserProfile getUserProfileById(Long userId) {
        Assert.notNull(userId, "User id cannot be null");
        log.info("Getting user details by id: {}", userId);
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Cannot find user by id: %s", userId)));
    }

    @Override
    public Optional<UserProfileDto> getUserProfileByEmail(String email) {
        Assert.notNull(email, "Email cannot be null");
        log.info("Getting user details by email: {}", email);
        return userProfileRepository.findByEmail(email)
                .map(UserServiceImpl::convertUserProfileDto);
    }

    @Override
    public UserProfileDto registerNewUserProfile(UserProfile userProfile) {
        log.info("Register new user: {}", userProfile.getUserName());
        if (userProfile.getId() != null) {
            log.error("Cannot register new user if user has id: {}", userProfile.getId());
            throw new BadRequestException("Cannot register new user if user exists");
        }
        return convertUserProfileDto(userProfileRepository.save(userProfile));
    }

    @Override
    public UserProfileDto updateNewUserProfile(UserProfileDto updateUserRequest) {
        log.info("Update user: {}", updateUserRequest.getId());
        if (updateUserRequest.getId() == null) {
            log.error("Cannot update user if user doesn't have an id");
            throw new BadRequestException("Cannot update user if user doesn't have an id");
        }
        var userProfile = userProfileRepository.findById(updateUserRequest.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format("Cannot find user by id: %s", updateUserRequest.getId())));
        userProfile.setUserName(updateUserRequest.getUserName());
        userProfile.setFirstName(updateUserRequest.getFirstName());
        userProfile.setLastName(updateUserRequest.getLastName());
        userProfile.setDefaultPictureUrl(updateUserRequest.getDefaultPictureUrl());
        return convertUserProfileDto(userProfileRepository.save(userProfile));
    }

    private static UserProfileDto convertUserProfileDto(UserProfile user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .defaultPictureUrl(user.getDefaultPictureUrl())
                .build();
    }
}
