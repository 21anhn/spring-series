package com.anhn.bookapi.controller;

import com.anhn.bookapi.dto.ApiResponse;
import com.anhn.bookapi.dto.request.LoginRequest;
import com.anhn.bookapi.dto.response.LoginResponse;
import com.anhn.bookapi.service.AuthenticationService;
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
    private AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        ApiResponse<LoginResponse> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.toString(), null);
        LoginResponse loginResponse = authenticationService.login(request);
        ResponseEntity<ApiResponse<LoginResponse>> responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        if (loginResponse != null) {
            response.setData(loginResponse);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return responseEntity;
    }
}
