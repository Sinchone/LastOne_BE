package com.lastone.core.config;

import com.lastone.core.security.exception.CustomAuthenticationEntryPoint;
import com.lastone.core.security.filter.AuthorizationFilter;
import com.lastone.core.security.filter.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtExceptionFilter jwtExceptionFilter;

    private final AuthorizationFilter authorizationFilter;

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(authorizationFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, AuthorizationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)

                .and()
                .authorizeRequests()
                .antMatchers("/test").permitAll()
                .antMatchers("/chat/test/chat-room/**").permitAll()
                .antMatchers("/chat/health").permitAll()
                .antMatchers("/chat/stomp/**").permitAll()
                .antMatchers("/pub/chat/message/**").permitAll()
                .antMatchers("/sub/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("access_token");
        configuration.addExposedHeader("refresh_token");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}