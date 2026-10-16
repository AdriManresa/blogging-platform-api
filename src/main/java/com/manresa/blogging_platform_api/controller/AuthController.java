package com.manresa.blogging_platform_api.controller;

import com.manresa.blogging_platform_api.dto.auth.AuthResponse;
import com.manresa.blogging_platform_api.dto.auth.LoginRequest;
import com.manresa.blogging_platform_api.dto.auth.RegisterRequest;
import com.manresa.blogging_platform_api.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
