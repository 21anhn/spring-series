package com.anhltn.auth_service.service.impl;

import com.anhltn.auth_service.dto.auth.LoginRequest;
import com.anhltn.auth_service.dto.auth.LoginResponse;
import com.anhltn.auth_service.dto.auth.RegisterRequest;
import com.anhltn.auth_service.entity.Role;
import com.anhltn.auth_service.entity.User;
import com.anhltn.auth_service.repository.RoleRepository;
import com.anhltn.auth_service.repository.UserRepository;
import com.anhltn.auth_service.security.JwtTokenProvider;
import com.anhltn.auth_service.service.AuthService;
import com.anhltn.common.exception.BadRequestException;
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

    @Autowired
    private RoleRepository roleRepository;

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
    public ApiResponse<Long> register(RegisterRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent())
            throw new BadRequestException("The email: " + request.getEmail() + " is already taken.");

        ApiResponse<Long> response = new ApiResponse<>();
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Optional<Role> optionalRole = roleRepository.findByName(com.anhltn.common.constant.Role.ROLE_USER.toString());
        optionalRole.ifPresent(user::setRole);

        userRepository.save(user);

        response.setData(user.getId());
        return response;
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
