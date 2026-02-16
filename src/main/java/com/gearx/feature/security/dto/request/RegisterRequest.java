package com.gearx.feature.security.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    @NotBlank String username;

    @Email @NotBlank String email;

    @NotBlank String password;

    private String fullName;
    private String address;
    private String phone; // optional
    private String createdBy; // optional
    private Integer isActive; // optional
    private Integer isDeleted; // optional
}
