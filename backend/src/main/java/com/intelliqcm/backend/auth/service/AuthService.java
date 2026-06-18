package com.intelliqcm.backend.auth.service;

import com.intelliqcm.backend.auth.dto.AuthResponse;
import com.intelliqcm.backend.auth.dto.LoginRequest;
import com.intelliqcm.backend.auth.dto.RegisterRequest;
import com.intelliqcm.backend.auth.model.User;
import com.intelliqcm.backend.auth.repository.UserRepository;
import com.intelliqcm.backend.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public void register(RegisterRequest request) {
        String verificationToken = UUID.randomUUID().toString();

        userRepository.findByEmail(request.getEmail()).ifPresentOrElse(existing -> {
            if (existing.isVerified()) {
                throw new IllegalArgumentException("Un compte avec cet email existe déjà.");
            }
            // Compte non vérifié : on renouvelle le token et renvoie l'email
            existing.setVerificationToken(verificationToken);
            userRepository.save(existing);
            emailService.sendVerificationEmail(existing.getEmail(), existing.getName(), verificationToken);
        }, () -> {
            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .verified(false)
                    .verificationToken(verificationToken)
                    .build();
            userRepository.save(user);
            emailService.sendVerificationEmail(user.getEmail(), user.getName(), verificationToken);
        });
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe incorrect."));

        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Ce compte utilise la connexion Google.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Email ou mot de passe incorrect.");
        }

        if (!user.isVerified()) {
            throw new EmailNotVerifiedException("Vérifie ton email avant de te connecter.");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }

    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Lien de vérification invalide ou expiré."));

        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    // Exception typée pour distinguer l'email non vérifié
    public static class EmailNotVerifiedException extends RuntimeException {
        public EmailNotVerifiedException(String message) { super(message); }
    }
}
