package com.anhn.bookapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LoginRequest {
    private String username, password;
}
