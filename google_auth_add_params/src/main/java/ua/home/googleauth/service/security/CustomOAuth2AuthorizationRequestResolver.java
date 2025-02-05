package ua.home.googleauth.service.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.Map;
import java.util.stream.Collectors;

public class CustomOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final DefaultOAuth2AuthorizationRequestResolver delegate;

    public CustomOAuth2AuthorizationRequestResolver(DefaultOAuth2AuthorizationRequestResolver delegate) {
        this.delegate = delegate;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = delegate.resolve(request);
        return patchState(oAuth2AuthorizationRequest, request);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = delegate.resolve(request, clientRegistrationId);
        return patchState(oAuth2AuthorizationRequest, request);
    }

    private OAuth2AuthorizationRequest patchState(OAuth2AuthorizationRequest auth2AuthorizationRequest, HttpServletRequest request) {
        if (auth2AuthorizationRequest == null) {
            return null;
        }
        var params = request.getParameterMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, value -> (Object) value.getValue()[0]));
        return OAuth2AuthorizationRequest.from(auth2AuthorizationRequest).additionalParameters(params).build();
    }
}
