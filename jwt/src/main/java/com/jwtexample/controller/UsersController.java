package com.jwtexample.controller;

import com.jwtexample.configuration.security.AuthUserDetails;
import com.jwtexample.controller.dto.UserDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Users")
@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    @GetMapping
    public UserDto getUser() {
        var user = ((AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserProfile();
        return UserDto
                .builder()
                .id(user.getId())
                .username(user.getUserName())
                .build();
    }
}
