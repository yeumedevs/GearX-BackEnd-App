package com.gearx.feature.security.mail;

import java.nio.charset.StandardCharsets;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromEmail;
    private final String fromName;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${mail.from.email}") String fromEmail,
            @Value("${mail.from.name}") String fromName) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
    }

    public void sendHtml(String toEmail, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                            StandardCharsets.UTF_8.name());

            helper.setFrom(new InternetAddress(fromEmail, fromName, StandardCharsets.UTF_8.name()));
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(html, true); // true = HTML

            mailSender.send(message);
        } catch (Exception e) {
            // ném lỗi rõ ràng để GlobalExceptionHandler bắt và trả ApiResponse
            Throwable root = e;
            while (root.getCause() != null) root = root.getCause();
            throw new RuntimeException("Send mail error (SMTP): " + root.getMessage(), e);
        }
    }
}
