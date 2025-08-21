package com.anhltn.auth_service.service.impl;

import com.anhltn.auth_service.dto.auth.LoginRequest;
import com.anhltn.auth_service.dto.auth.LoginResponse;
import com.anhltn.auth_service.dto.auth.RegisterRequest;
import com.anhltn.auth_service.entity.User;
import com.anhltn.auth_service.repository.UserRepository;
import com.anhltn.auth_service.security.JwtTokenProvider;
import com.anhltn.auth_service.service.AuthService;
import com.anhltn.common.exception.UnauthorizedException;
import com.anhltn.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        ApiResponse<LoginResponse> response = new ApiResponse<>();
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty() ||
                !passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
            throw new UnauthorizedException("Invalid credentials for email: " + request.getEmail());
        }

        String accessToken = jwtTokenProvider.generateToken(
                request.getEmail(),
                optionalUser.get().getRole().getName(),
                false
        );

        String refreshToken = jwtTokenProvider.generateToken(
                request.getEmail(),
                optionalUser.get().getRole().getName(),
                true
        );

        LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);
        response.setData(loginResponse);

        return response;
    }

    @Override
    public void register(RegisterRequest request) {

    }

    @Override
    public void forgetPassword(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }

    @Override
    public void logout(String token) {

    }

    @Override
    public String refreshToken(String refreshToken) {
        return "";
    }
}
