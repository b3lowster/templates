package com.jwtexample.controller;

import com.jwtexample.configuration.security.JwtTokenUtil;
import com.jwtexample.controller.dto.AuthRequest;
import com.jwtexample.model.dto.UserTokenDetails;
import com.jwtexample.controller.dto.AuthRefreshRequest;
import com.jwtexample.controller.dto.AuthResponse;
import com.jwtexample.model.exception.BadRequestException;
import com.jwtexample.model.exception.ExpiredException;
import com.jwtexample.model.types.TokenType;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = "Authentication")
@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping(path = "/login")
    public AuthResponse getAuthByTelegramInitData(@RequestBody AuthRequest request) {
        log.debug("Auth request: {}", request);

        try {
            var tokenDetail = UserTokenDetails.builder()
                    .id(1L)
                    .userName("Test User Name")
                    .lastName("Test Last Name")
                    .firstName("First Name")
                    .build();
            String accessToken = jwtTokenUtil.generateAccessToken(tokenDetail);
            String refreshToken = jwtTokenUtil.generateRefreshToken(tokenDetail);
            return new AuthResponse(accessToken, refreshToken);
        } catch (IOException e) {
            throw new BadRequestException(String.format("Error while generating access token. The reason is %s", e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public AuthResponse refreshAccessToken(@RequestBody @Valid AuthRefreshRequest request) {
        final String refreshToken = request.getRefreshToken();
        if (!jwtTokenUtil.validate(refreshToken, TokenType.refresh)) {
            log.error("Refresh token {} is not valid", refreshToken);
            throw new ExpiredException("Refresh token is not valid");
        }
        Long id = jwtTokenUtil.getId(refreshToken);
        var tokenDetail = UserTokenDetails.builder()
                .id(id)
                .userName("Test User Name")
                .lastName("Test Last Name")
                .firstName("First Name")
                .build();

        try {
            String accessToken = jwtTokenUtil.generateAccessToken(tokenDetail);
            String newRefreshToken = jwtTokenUtil.generateRefreshToken(tokenDetail);
            return new AuthResponse(accessToken, newRefreshToken);
        } catch (IOException e) {
            log.error("Error while generating access token", e);
            throw new BadRequestException(String.format("Error while generating access token. The reason is %s", e.getMessage()));
        }
    }
}
