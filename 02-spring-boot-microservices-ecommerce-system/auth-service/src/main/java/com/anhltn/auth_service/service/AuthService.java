package com.anhltn.auth_service.service;

import com.anhltn.auth_service.dto.auth.LoginRequest;
import com.anhltn.auth_service.dto.auth.LoginResponse;
import com.anhltn.auth_service.dto.auth.RegisterRequest;
import com.anhltn.common.response.ApiResponse;

public interface AuthService {
    ApiResponse<LoginResponse> login(LoginRequest request);

    ApiResponse<Long> register(RegisterRequest request);

    void forgetPassword(String email);

    void resetPassword(String token, String newPassword);

    void logout(String token);

    String refreshToken(String refreshToken);
}
