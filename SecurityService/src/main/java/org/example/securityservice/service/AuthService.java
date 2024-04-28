package org.example.securityservice.service;


import lombok.AllArgsConstructor;
import org.example.securityservice.models.Role;
import org.example.securityservice.models.User;
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

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        //do validation if user exists in DB
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
//        UserVO registeredUser = restTemplate.postForObject("http://user-service/users", request, UserVO.class);

        var userId = "userId";
        var role = "role";
        String accessToken = jwtUtil.generate(userId, role, "ACCESS");
        String refreshToken = jwtUtil.generate(userId, role, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // todo: add logic to fetch user from DB here
        return User.builder()
                .id(1)
                .username("admin@gmail.com")
                .password(passwordEncoder.encode("admin1234"))
                .role(Role.USER)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .credentialsNonExpired(true)
                .build();
    }
}
