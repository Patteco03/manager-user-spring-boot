package com.example.manage_user.controllers;

import jakarta.validation.Valid;
import com.example.manage_user.dto.LoginRequestDTO;
import com.example.manage_user.dto.UserRequestDTO;
import com.example.manage_user.dto.UserResponseDTO;
import com.example.manage_user.security.auth.AuthService;
import com.example.manage_user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    private final UserService userService;

    public AuthController(AuthService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequestDTO request) {
        String token = service.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(service.me(email));
    }
}
