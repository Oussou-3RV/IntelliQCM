package com.intelliqcm.backend.auth.security;

import com.intelliqcm.backend.auth.model.User;
import com.intelliqcm.backend.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final String FRONTEND_REDIRECT = "http://localhost:5173/auth/callback";

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name  = oAuth2User.getAttribute("name");

        // Trouve ou crée l'utilisateur
        User user = userRepository.findByEmail(email).orElseGet(() ->
            userRepository.save(
                User.builder()
                    .email(email)
                    .name(name != null ? name : email)
                    .provider("google")
                    .build()
            )
        );

        String token = jwtUtil.generateToken(user.getEmail());

        // Redirige vers le frontend avec le token dans l'URL
        response.sendRedirect(FRONTEND_REDIRECT + "?token=" + token);
    }
}
