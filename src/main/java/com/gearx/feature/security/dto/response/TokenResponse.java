package com.gearx.feature.security.dto.response;

import java.time.Instant;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResponse {

    String token;
    Instant issuedAt; // epoch seconds
    Instant expiresAt; // epoch seconds
    String issuer;
    String username;
    String role;
}
