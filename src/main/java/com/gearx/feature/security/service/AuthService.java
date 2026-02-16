package com.gearx.feature.security.service;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.gearx.common.exception.AppException;
import com.gearx.common.exception.ErrorCode;
import com.gearx.feature.security.dto.request.LoginRequest;
import com.gearx.feature.security.dto.response.TokenResponse;
import com.gearx.feature.security.mapper.RevokedTokenMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RevokedTokenMapper revokedTokenMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TokenResponse login(LoginRequest req) {
        Authentication auth =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                req.getUsername(), req.getPassword()));
        // nếu sai sẽ ném AuthenticationException và GlobalExceptionHandler đã map BAD_CREDENTIALS
        
        com.gearx.feature.security.util.CustomUserDetails userDetails = (com.gearx.feature.security.util.CustomUserDetails) auth.getPrincipal();
        String role = userDetails.getUser().getRole() != null ? userDetails.getUser().getRole().getRoleCode() : null;

        String token = jwtService.generateToken(req.getUsername(), role);
        Instant issueAt = jwtService.getIssuedAt(token);
        Instant expiration = jwtService.getExpiration(token);

        return TokenResponse.builder()
                .token(token)
                .issuedAt(issueAt)
                .expiresAt(expiration)
                .issuer(jwtService.getIssuer())
                .username(req.getUsername())
                .role(role)
                .build();
    }

    public void logout(String token) {

        if (!jwtService.isValid(token)) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        if (revokedTokenMapper.isRevoked(token) > 0) {
            // đã logout rồi coi như thành công idempotent
            return;
        }

        LocalDateTime exp =
                LocalDateTime.ofInstant(
                        jwtService.getExpiration(token), java.time.ZoneId.systemDefault());
        revokedTokenMapper.insert(token, exp);
        // dọn rác token quá hạn
        revokedTokenMapper.deleteExpired();
    }

    public TokenResponse introspect(String token) {

        if (token == null || token.isBlank()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        if (!jwtService.isValid(token) || revokedTokenMapper.isRevoked(token) > 0) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        String username =
                jwtService
                        .extractUsername(token)
                        .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        String role = jwtService.extractRole(token).orElse(null);

        return TokenResponse.builder()
                .token(token)
                .issuedAt(jwtService.getIssuedAt(token))
                .expiresAt(jwtService.getExpiration(token))
                .issuer(jwtService.getIssuer())
                .username(username)
                .role(role)
                .build();
    }
}
