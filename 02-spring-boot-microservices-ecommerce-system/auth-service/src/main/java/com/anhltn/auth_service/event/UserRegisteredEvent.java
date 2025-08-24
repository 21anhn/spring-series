package com.anhltn.auth_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredEvent {
    private Long id;
    private String fullName;
    private String email, password;
    private String phoneNumber;
    private String role;
}
