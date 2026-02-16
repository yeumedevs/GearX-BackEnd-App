package com.gearx.feature.security.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gearx.common.constants.ApiConstants;
import com.gearx.common.response.ApiResponse;
import com.gearx.common.response.ResponseHandler;
import com.gearx.feature.security.dto.request.ForgotPasswordRequest;
import com.gearx.feature.security.dto.request.IntrospectRequest;
import com.gearx.feature.security.dto.request.LoginRequest;
import com.gearx.feature.security.dto.request.ResetPasswordRequest;
import com.gearx.feature.security.dto.response.TokenResponse;
import com.gearx.feature.security.service.AuthService;
import com.gearx.feature.security.service.PasswordResetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiConstants.Auth.BASE)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    // Login
    @PostMapping(ApiConstants.Auth.LOGIN)
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest req) {
        TokenResponse tr = authService.login(req);
        return ResponseHandler.success(tr);
    }

    // Logout (Token có thể lấy từ Authorization hoặc body)
    @PostMapping(ApiConstants.Auth.LOGOUT)
    public ResponseEntity<ApiResponse<Object>> logout(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody(required = false) IntrospectRequest body) {
        String token = extractToken(authorization, body);
        authService.logout(token);
        return ResponseHandler.success("Logout thành công", null);
    }

    // Introspect
    @PostMapping(ApiConstants.Auth.INTROSPECT)
    public ResponseEntity<ApiResponse<TokenResponse>> introspect(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody(required = false) IntrospectRequest req) {
        String token = extractToken(authorization, req);
        TokenResponse tr = authService.introspect(token);
        return ResponseHandler.success("Token hợp lệ", tr);
    }

    // Gửi OTP
    @PostMapping(ApiConstants.Auth.FORGOT_REQUEST)
    public ResponseEntity<ApiResponse<Object>> forgotRequest(
            @Valid @RequestBody ForgotPasswordRequest req) {
        passwordResetService.sendOtp(req.getEmail());
        return ResponseHandler.success("Nếu email tồn tại, OTP đã được gửi", null);
    }

    // Reset password
    @PostMapping(ApiConstants.Auth.FORGOT_RESET)
    public ResponseEntity<ApiResponse<Object>> forgotReset(
            @Valid @RequestBody ResetPasswordRequest req) {
        passwordResetService.resetPassword(req.getEmail(), req.getOtp(), req.getNewPassword());
        return ResponseHandler.success("Đổi mật khẩu thành công", null);
    }

    private String extractToken(String authorization, IntrospectRequest req) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        if (req != null && req.getToken() != null && !req.getToken().isBlank()) {
            return req.getToken();
        }
        return null;
    }
}
