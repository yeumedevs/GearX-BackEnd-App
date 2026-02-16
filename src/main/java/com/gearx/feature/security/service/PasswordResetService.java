package com.gearx.feature.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gearx.common.exception.AppException;
import com.gearx.common.exception.ErrorCode;
import com.gearx.feature.security.entity.User;
import com.gearx.feature.security.mail.EmailService;
import com.gearx.feature.security.mail.PasswordResetTemplate;
import com.gearx.feature.security.mapper.UserMapper;

@Service
public class PasswordResetService {

    private final UserMapper userMapper;
    private final OtpService otpService;
    private final EmailService emailService;
    private final PasswordEncoder encoder;
    private final String appName;
    private final String baseUrl;

    public PasswordResetService(
            UserMapper userMapper,
            OtpService otpService,
            EmailService emailService,
            PasswordEncoder encoder,
            @Value("${app.name}") String appName,
            @Value("${app.base-url}") String baseUrl) {
        this.userMapper = userMapper;
        this.otpService = otpService;
        this.emailService = emailService;
        this.encoder = encoder;
        this.appName = appName;
        this.baseUrl = baseUrl;
    }

    public void sendOtp(String email) {
        User user = userMapper.findByEmail(email);
        if (user == null || (user.getIsDeleted() != null && user.getIsDeleted() == 1)) {
            // Ẩn thông tin tồn tại account để an toàn
            return; // vẫn trả success
        }

        String username = user.getUsername();
        String otp = otpService.generateAndStore(email);
        String html = PasswordResetTemplate.build(appName, username, otp, baseUrl + "/support");
        emailService.sendHtml(email, "[%s] OTP đổi mật khẩu".formatted(appName), html);
    }

    public void resetPassword(String email, String otp, String newPassword) {
        User user = userMapper.findByEmail(email);
        if (user == null || (user.getIsDeleted() != null && user.getIsDeleted() == 1)) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        boolean ok = otpService.verify(email, otp);
        if (!ok) {
            throw new AppException(ErrorCode.OTP_INVALID);
        }
        // update password
        String hash = encoder.encode(newPassword);
        int rows = userMapper.updatePasswordById(email, user.getUserId(), hash);
        if (rows <= 0) throw new AppException(ErrorCode.INTERNAL_ERROR);
        otpService.invalidate(email);
    }
}
