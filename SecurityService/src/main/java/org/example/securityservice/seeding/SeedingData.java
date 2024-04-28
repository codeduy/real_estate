package org.example.securityservice.seeding;

import org.example.securityservice.models.Role;
import org.example.securityservice.models.User;
import org.example.securityservice.repositories.UserRepository;
import org.example.securityservice.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SeedingData {
    @Bean
    public CommandLineRunner seedData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        System.out.println("Seeding data...");
        return args -> {
            var admin = User.builder()
                    .username("admin@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.ADMIN)
                    .accountNonExpired(true)
                    .credentialsNonExpired(true)
                    .accountNonLocked(true)
                    .enabled(true)
                    .build();
            userRepository.save(admin);
        };
    }
}
