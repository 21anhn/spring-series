package com.anhltn.auth_service.controller;

import com.anhltn.auth_service.dto.auth.LoginRequest;
import com.anhltn.auth_service.dto.auth.LoginResponse;
import com.anhltn.auth_service.dto.auth.RegisterRequest;
import com.anhltn.auth_service.service.AuthService;
import com.anhltn.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        ApiResponse<LoginResponse> response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        ApiResponse<Long> response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
