package com.gearx.feature.security.service;

import java.security.SecureRandom;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private final StringRedisTemplate redis;
    private final int ttlSeconds;
    private final int resendWindowSeconds;
    private final int maxAttempts;
    private final SecureRandom rnd = new SecureRandom();

    public OtpService(
            StringRedisTemplate redis,
            @Value("${app.otp.ttl-seconds:300}") int ttlSeconds,
            @Value("${app.otp.resend-window-seconds:60}") int resendWindowSeconds,
            @Value("${app.otp.max-attempts:5}") int maxAttempts) {
        this.redis = redis;
        this.ttlSeconds = ttlSeconds;
        this.resendWindowSeconds = resendWindowSeconds;
        this.maxAttempts = maxAttempts;
    }

    private String keyOtp(String email) {
        return "otp:fp:" + email.toLowerCase();
    }

    private String keyCooldown(String email) {
        return "otp:fp:cool:" + email.toLowerCase();
    }

    private String keyAttempts(String email) {
        return "otp:fp:attempt:" + email.toLowerCase();
    }

    public String generateAndStore(String email) {
        // kiểm tra cooldown resend
        String coolKey = keyCooldown(email);
        if (Boolean.TRUE.equals(redis.hasKey(coolKey))) {
            throw new IllegalStateException("Vui lòng đợi trước khi yêu cầu OTP mới.");
        }

        String otp = String.format("%06d", rnd.nextInt(1_000_000));
        redis.opsForValue().set(keyOtp(email), otp, Duration.ofSeconds(ttlSeconds));
        // cooldown resend
        redis.opsForValue().set(coolKey, "1", Duration.ofSeconds(resendWindowSeconds));
        // reset attempts
        redis.delete(keyAttempts(email));
        return otp;
    }

    public boolean verify(String email, String otp) {
        String otpKey = keyOtp(email);
        String attemptsKey = keyAttempts(email);

        // Không có OTP (hết hạn/không yêu cầu) -> fail sớm, không tăng attempts
        String expected = redis.opsForValue().get(otpKey);
        if (expected == null) return false;

        // Atomic INCR để tránh race; set TTL cho attempts ở lần đầu
        Long count = redis.opsForValue().increment(attemptsKey); // trả về giá trị sau khi tăng
        if (count != null && count == 1L) {
            redis.expire(attemptsKey, Duration.ofSeconds(ttlSeconds));
        }
        if (count != null && count > maxAttempts) {
            return false;
        }

        boolean ok = expected.equals(otp);
        if (ok) {
            // Xoá OTP nếu đúng
            redis.delete(otpKey);
            redis.delete(attemptsKey);
        }
        return ok;
    }

    public void invalidate(String email) {
        redis.delete(keyOtp(email));
        redis.delete(keyAttempts(email));
    }
}
