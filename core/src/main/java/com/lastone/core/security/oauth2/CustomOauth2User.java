package com.lastone.core.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomOauth2User implements OAuth2User {

    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomOauth2User(String email, Map<String, Object> attributes) {
        this.email = email;
        this.attributes = attributes;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    private Map<String, Object> attributes;


    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.email;
    }
}
