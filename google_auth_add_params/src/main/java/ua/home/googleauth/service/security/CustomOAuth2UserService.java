package ua.home.googleauth.service.security;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ua.home.googleauth.model.UserProfile;

import static ua.home.googleauth.model.Constants.COMPANY_ID_ATTRIBUTE_NAME;


@Service

public class CustomOAuth2UserService extends DefaultOAuth2UserService  {

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);
		return processOAuth2User(userRequest, user);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		var companyId = oAuth2UserRequest.getAdditionalParameters().get(COMPANY_ID_ATTRIBUTE_NAME);
		if (companyId == null || StringUtils.isEmpty(companyId.toString())) {
			throw new OAuth2AuthenticationException("Required parameter 'publicCompanyId' is missing");
		}


		var userProfile = new UserProfile();
		userProfile.setId(1L);
		userProfile.setUserName(oAuth2User.getAttribute("name"));
		userProfile.setFirstName(oAuth2User.getAttribute("given_name"));
		userProfile.setLastName(oAuth2User.getAttribute("family_name"));
		userProfile.setEmail(oAuth2User.getAttribute("email"));
		userProfile.setDefaultPictureUrl(oAuth2User.getAttribute("picture"));
		userProfile.setCompanyId(companyId.toString());

		return ua.home.googleauth.model.security.UserPrincipal.create(userProfile, oAuth2User.getAttributes());
	}
}
