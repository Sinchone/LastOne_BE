package com.lastone.core.security;

import com.lastone.core.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Builder
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String nickname;

    private String email;

    private String gender;

    private String profileUrl;

    private String workoutPurpose;

    private String workoutTime;

    private String workoutDay;

    public static UserDetails convert(Member member) {
        return UserDetailsImpl.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .gender(member.getGender())
                .profileUrl(member.getProfileUrl())
                .workoutPurpose(member.getWorkoutPurpose())
                .workoutTime(member.getWorkoutTime())
                .workoutDay(member.getWorkoutDay())
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "ROLE_MEMBER");
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
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
}
