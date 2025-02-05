package ua.home.googleauth.model.security;

import ua.home.googleauth.model.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {

    private final Long id;
    private final String email;
    private final String companyId;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    public UserPrincipal(Long id, String email, String companyId, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.companyId = companyId;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UserProfile user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority(DEFAULT_ROLE_PREFIX+"user"));

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getCompanyId(),
                authorities
        );
    }

    public static UserPrincipal create(UserProfile user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyId() {
        return companyId;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
