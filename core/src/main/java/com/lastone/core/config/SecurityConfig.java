package com.lastone.core.config;

import com.lastone.core.security.filter.AuthorizationFilter;
import com.lastone.core.security.oauth2.Oauth2AuthenticationSuccessHandler;
import com.lastone.core.security.oauth2.Oauth2UserService;
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

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final Oauth2UserService oauth2UserService;
    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final AuthorizationFilter authorizationFilter;

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
                .oauth2Login().disable();
                /*.authorizationEndpoint().baseUri("/oauth2/authorize")
                .and()
                .redirectionEndpoint().baseUri("/oauth2/callback/**")
                .and()
                .userInfoEndpoint().userService(oauth2UserService)
                .and()
                .successHandler(oauth2AuthenticationSuccessHandler)

                .and()
                .addFilterBefore(authorizationFilter,UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .authorizeRequests()

                .anyRequest().permitAll()
                .antMatchers("/test").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                ;
                */



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