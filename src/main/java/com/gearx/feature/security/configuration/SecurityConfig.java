package com.gearx.feature.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gearx.feature.security.util.JsonAccessDeniedHandler;
import com.gearx.feature.security.util.JsonAuthEntryPoint;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final JsonAuthEntryPoint entryPoint;
    private final JsonAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter,
            JsonAuthEntryPoint entryPoint,
            JsonAccessDeniedHandler adh) {
        this.jwtFilter = jwtFilter;
        this.entryPoint = entryPoint;
        this.accessDeniedHandler = adh;
    }

    protected static final String[] WHITE_LIST_ENDPOINTS = {
        // AUTH API
        "/api/v1/auth/login",
        "/api/v1/auth/logout",
        "/api/v1/auth/introspect",
        "/api/v1/auth/forgot/request",
        "/api/v1/auth/forgot/reset",

        // User API đáng ra phải xoá cái này đi
        "/api/v1/user/register",

        // Admin
        // "api/v1/user/delete"

        // PayOS
        "/api/webhooks/payos",
        "/payos/return",

        // Chat
        "/api/v1/ai/chat",
        "/api/v1/ai/chat-with-image",
        "/api/v1/ai/help",

        // Monitoring
        "/actuator/**",

        // OpenAPI & Swagger UI
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(
                        sm ->
                                sm.sessionCreationPolicy(
                                        org.springframework.security.config.http
                                                .SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(WHITE_LIST_ENDPOINTS)
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .exceptionHandling(
                        eh ->
                                eh.authenticationEntryPoint(entryPoint)
                                        .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg)
            throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
