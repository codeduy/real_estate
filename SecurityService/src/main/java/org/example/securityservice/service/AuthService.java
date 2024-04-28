package org.example.securityservice.service;


import lombok.AllArgsConstructor;
import org.example.securityservice.models.Role;
import org.example.securityservice.models.User;
import org.example.securityservice.repositories.UserRepository;
import org.example.securityservice.requests.AuthenticateRequest;
import org.example.securityservice.requests.RegisterRequest;
import org.example.securityservice.responses.AuthResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthResponse register(RegisterRequest request) {
        var userInDb = userRepository.findByUsername(request.getEmail());
        if (userInDb.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var createdUser = userRepository.save(user);

        var userId = createdUser.getId().toString();
        var role = createdUser.getRole().toString();

        String accessToken = jwtUtil.generate(userId, role, "ACCESS");
        String refreshToken = jwtUtil.generate(userId, role, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
