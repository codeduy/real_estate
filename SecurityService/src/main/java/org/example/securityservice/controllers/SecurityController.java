package org.example.securityservice.controllers;

import lombok.AllArgsConstructor;
import org.example.securityservice.entities.AuthRequest;
import org.example.securityservice.entities.AuthResponse;
import org.example.securityservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@AllArgsConstructor
public class SecurityController {

    private final AuthService authService;

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        // work ok :))
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping
    public String hello() {
        return "Hello from Security Service";
    }
}
