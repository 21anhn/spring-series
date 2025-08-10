package com.anhn.bookapi.service.impl;

import com.anhn.bookapi.constant.Role;
import com.anhn.bookapi.dto.request.LoginRequest;
import com.anhn.bookapi.dto.request.RegisterRequest;
import com.anhn.bookapi.dto.response.LoginResponse;
import com.anhn.bookapi.dto.response.RegisterResponse;
import com.anhn.bookapi.entity.User;
import com.anhn.bookapi.repository.RoleRepository;
import com.anhn.bookapi.repository.UserRepository;
import com.anhn.bookapi.security.JwtProvider;
import com.anhn.bookapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    @Override
    public RegisterResponse register(RegisterRequest request) {
        User user = new User();

        user.setUsername(request.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail().toLowerCase());
        user.setRoles(Set.of(roleRepository.findByName(Role.ROLE_ADMIN.toString())));

        userRepository.save(user);

        RegisterResponse response = new RegisterResponse(user.getId(),
                user.getUsername(),
                user.getEmail());

        return response;
    }
}
