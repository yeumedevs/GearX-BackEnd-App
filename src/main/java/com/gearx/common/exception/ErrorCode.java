package com.gearx.common.exception;

import lombok.Getter;

/**
 * Mã lỗi hệ thống chia theo nhóm: - 5000: Lỗi hệ thống / bảo mật / xác thực - 4000: Lỗi từ dịch vụ
 * bên ngoài (Google, Vonage,...) - 3000: Lỗi định dạng / dữ liệu không hợp lệ - 2000: Không tìm
 * thấy / đã tồn tại
 */
@Getter
public enum ErrorCode {
    NOT_FOUND("2000", "Không tìm thấy"),
    ALREADY_EXIST("2000", "Đã tồn tại"),
    USERNAME_ALREADY_EXIST("2000", "Username đã tồn tại"),
    EMAIL_ALREADY_EXIST("2000", "Email đã tồn tại"),
    OTP_INVALID("2000", "OTP không hợp lệ"),
    INVALID_TOKEN("2000", "Token không hợp lệ"),
    PRODUCT_NOT_FOUND("2000", "Không tìm thấy sản phẩm"),
    PRODUCT_EXISTED("2000", "Sản phẩm đã tồn tại"),
    PRODUCT_EMPTY("2000", "Không có sản phẩm tồn tại"),

    // =========== SYSTEM ============
    BAD_SQL("1001", "Sai câu lệnh SQL"),
    INTERNAL_ERROR("5000", "Lỗi hệ thống"),
    UNAUTHORIZED("5000", "Lỗi xác minh"),
    BAD_CREDENTIALS("5000", "Sai thông tin đăng nhập"),

    FORBIDDEN("5000", "Không được phép truy cập");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
