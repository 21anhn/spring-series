package com.anhn.bookapi.service;

import com.anhn.bookapi.dto.request.LoginRequest;
import com.anhn.bookapi.dto.request.RegisterRequest;
import com.anhn.bookapi.dto.response.LoginResponse;
import com.anhn.bookapi.dto.response.RegisterResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest request);
}
