package com.anhn.bookapi.service;

import com.anhn.bookapi.dto.request.LoginRequest;
import com.anhn.bookapi.dto.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
}
