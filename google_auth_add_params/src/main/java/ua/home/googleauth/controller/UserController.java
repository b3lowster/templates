package ua.home.googleauth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.home.googleauth.model.dto.UserProfileDto;
import ua.home.googleauth.model.security.UserPrincipal;


@RestController
@RequestMapping("/api/users")

public class UserController {

    @GetMapping()
    public UserProfileDto getUserDetails() {
        var userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userProfileDto = new UserProfileDto();
        userProfileDto.setEmail(userPrincipal.getEmail());
        userProfileDto.setUserName(userPrincipal.getUsername());
        userProfileDto.setFirstName(userPrincipal.getName());
        userProfileDto.setCompanyId(userPrincipal.getCompanyId());
        return userProfileDto;
    }
}
