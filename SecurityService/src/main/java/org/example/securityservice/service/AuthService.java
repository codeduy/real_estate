package org.example.securityservice.service;


import lombok.AllArgsConstructor;
import org.example.securityservice.entities.AuthRequest;
import org.example.securityservice.entities.AuthResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthResponse register(AuthRequest request) {
        //do validation if user exists in DB
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
//        UserVO registeredUser = restTemplate.postForObject("http://user-service/users", request, UserVO.class);

        var userId = "userId";
        var role = "role";
        String accessToken = jwtUtil.generate(userId, role, "ACCESS");
        String refreshToken = jwtUtil.generate(userId, role, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

}
