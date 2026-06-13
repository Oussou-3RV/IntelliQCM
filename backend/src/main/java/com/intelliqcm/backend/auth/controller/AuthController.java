package com.intelliqcm.backend.auth.controller;

import com.intelliqcm.backend.auth.dto.AuthResponse;
import com.intelliqcm.backend.auth.dto.LoginRequest;
import com.intelliqcm.backend.auth.dto.RegisterRequest;
import com.intelliqcm.backend.auth.model.User;
import com.intelliqcm.backend.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(Map.of("message", "Un email de confirmation a été envoyé à " + request.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(Map.of("message", "Email vérifié avec succès !"));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Map.of(
            "name",  user.getName(),
            "email", user.getEmail(),
            "role",  user.getRole().name()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleError(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(AuthService.EmailNotVerifiedException.class)
    public ResponseEntity<Map<String, String>> handleUnverified(AuthService.EmailNotVerifiedException e) {
        return ResponseEntity.status(403).body(Map.of("message", e.getMessage(), "unverified", "true"));
    }
}
