package com.anhn.bookapi.config;

import com.anhn.bookapi.entity.Role;
import com.anhn.bookapi.entity.User;
import com.anhn.bookapi.repository.RoleRepository;
import com.anhn.bookapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = roleRepository.save(new Role(null, "ROLE_ADMIN", null));
            roleRepository.save(new Role(null, "ROLE_USER", null));

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123456"))
                    .email("admin@gmail.com")
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(admin);
        }
    }
}
