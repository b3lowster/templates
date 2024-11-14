package com.example.service.security;

import com.example.model.db.UserProfile;
import com.example.model.dto.UserProfileDto;
import com.example.model.types.UserRole;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {

	private final UserService userService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);
		return processOAuth2User(userRequest, user);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		var userProfile = new UserProfile();
		userProfile.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
		userProfile.setEmail(oAuth2User.getAttribute("email"));
		userProfile.setUserName(oAuth2User.getAttribute("name"));
		userProfile.setFirstName(oAuth2User.getAttribute("given_name"));
		userProfile.setLastName(oAuth2User.getAttribute("family_name"));
		userProfile.setDefaultPictureUrl(oAuth2User.getAttribute("picture"));
		userProfile.setRole(UserRole.user);

		log.trace("User info is {}", userProfile);
		Optional<UserProfileDto> userOptional = userService.getUserProfileByEmail(userProfile.getEmail());
		var userProfileDto = userOptional
				.map(userService::updateNewUserProfile)
				.orElseGet(() -> userService.registerNewUserProfile(userProfile));
		var existerUserProfile = userService.getUserProfileById(userProfileDto.getId());
		return com.example.model.security.UserPrincipal.create(existerUserProfile, oAuth2User.getAttributes());
	}
}
