package org.example.securityservice.controllers;

import lombok.AllArgsConstructor;
import org.example.securityservice.models.User;
import org.example.securityservice.requests.AuthenticateRequest;
import org.example.securityservice.requests.RegisterRequest;
import org.example.securityservice.responses.AuthResponse;
import org.example.securityservice.service.AuthService;
import org.example.securityservice.service.JwtUtil;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/auth/")
@AllArgsConstructor
public class SecurityController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthenticateRequest request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var username = (String) authentication.getName();
            var user = (User) authService.loadUserByUsername(username);
            String accessToken = jwtUtil.generate(user.getId().toString(), user.getRole().toString(), "ACCESS");
            String refreshToken = jwtUtil.generate(user.getId().toString(), user.getRole().toString(), "REFRESH");

            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
