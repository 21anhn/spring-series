package com.anhn.bookapi.service.impl;

import com.anhn.bookapi.dto.ApiResponse;
import com.anhn.bookapi.dto.request.LoginRequest;
import com.anhn.bookapi.dto.response.LoginResponse;
import com.anhn.bookapi.entity.User;
import com.anhn.bookapi.repository.UserRepository;
import com.anhn.bookapi.security.JwtProvider;
import com.anhn.bookapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return null;
        }

        String token = jwtProvider.generateToken(request.getUsername(), user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toList()));

        return new LoginResponse(token);
    }
}
