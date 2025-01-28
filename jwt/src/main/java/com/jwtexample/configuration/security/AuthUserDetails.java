package com.jwtexample.configuration.security;

import com.jwtexample.model.UserProfile;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class AuthUserDetails implements UserDetails {

    private boolean enabled = true;

    private String username;
    private String password;
    private String fullName;
    private UserProfile userProfile;
    private final Set<GrantedAuthority> authorities = new HashSet<>();

    public AuthUserDetails() {
    }

    public AuthUserDetails(String username, String password, UserProfile userProfile) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        this.userProfile = userProfile;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        authorities.add(new SimpleGrantedAuthority(Role.USER));
        return authorities;
    }
}
