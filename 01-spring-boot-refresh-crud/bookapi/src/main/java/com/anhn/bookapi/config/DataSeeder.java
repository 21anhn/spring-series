package com.anhn.bookapi.config;

import com.anhn.bookapi.entity.Role;
import com.anhn.bookapi.entity.User;
import com.anhn.bookapi.repository.RoleRepository;
import com.anhn.bookapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public DataSeeder(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = roleRepository.save(new Role(null, com.anhn.bookapi.constant.Role.ROLE_ADMIN.toString(), null));
            roleRepository.save(new Role(null, com.anhn.bookapi.constant.Role.ROLE_USER.toString(), null));

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
