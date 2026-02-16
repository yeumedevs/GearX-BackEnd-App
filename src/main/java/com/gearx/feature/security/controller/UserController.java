package com.gearx.feature.security.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gearx.common.constants.ApiConstants;
import com.gearx.common.response.ApiResponse;
import com.gearx.common.response.ResponseHandler;
import com.gearx.feature.security.dto.request.RegisterRequest;
import com.gearx.feature.security.entity.User;
import com.gearx.feature.security.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiConstants.User.BASE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Register
    @PostMapping(ApiConstants.User.REGISTER)
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegisterRequest req) {
        int rows = userService.register(req);
        if (rows > 0) {
            return ResponseHandler.success("Tạo user thành công", null);
        }
        return ResponseHandler.error(
                "Tạo user thất bại",
                com.gearx.common.exception.ErrorCode.INTERNAL_ERROR,
                org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(ApiConstants.User.DELETE)
    public ResponseEntity<ApiResponse<Object>> deleteUser(@Valid @RequestParam int id) {
        userService.deleteUserById(id);
        return ResponseHandler.success("Đã xoá tài khoản với id " + id + " thành công !", id);
    }

    @GetMapping(ApiConstants.User.FETCH)
    public ResponseEntity<ApiResponse<User>> fetchUserData(@Valid @RequestParam String username) {
        User u = userService.fetchDataByUsername(username);
        return ResponseHandler.success("Fetch data thành công cho " + username, u);
    }

    @PutMapping(ApiConstants.User.UPDATE)
    public ResponseEntity<ApiResponse<Object>> updateUser(
            @Valid @RequestParam int userId, @RequestBody User user) {
        int updated = userService.updateUserByUserId(userId, user);
        return ResponseHandler.success("Cập nhật thông tin thành công !", updated);
    }
}
