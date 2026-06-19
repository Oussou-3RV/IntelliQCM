package com.intelliqcm.backend.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailService {

    private final WebClient webClient;
    private final String apiKey;
    private final String from;
    private final String frontendUrl;

    public EmailService(
            @Value("${resend.api.key}") String apiKey,
            @Value("${resend.from}") String from,
            @Value("${app.frontend-url:http://localhost:5173}") String frontendUrl
    ) {
        this.apiKey = apiKey;
        this.from = from;
        this.frontendUrl = frontendUrl;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.resend.com")
                .build();
    }

    public void sendVerificationEmail(String toEmail, String name, String token) {
        String verifyUrl = frontendUrl + "/verify-email?token=" + token;

        String html = """
            <div style="font-family:sans-serif;max-width:480px;margin:auto;padding:32px;background:#111827;color:#f9fafb;border-radius:12px">
              <h1 style="font-size:24px;font-weight:900;margin-bottom:8px">Confirme ton adresse email</h1>
              <p style="color:#9ca3af;margin-bottom:24px">Bonjour %s, clique sur le bouton ci-dessous pour activer ton compte QuizAI.</p>
              <a href="%s"
                 style="display:inline-block;background:#4f46e5;color:white;text-decoration:none;padding:12px 28px;border-radius:8px;font-weight:600;font-size:15px">
                Vérifier mon email
              </a>
              <p style="color:#6b7280;font-size:12px;margin-top:24px">Ce lien expire dans 24h. Si tu n'as pas créé de compte, ignore cet email.</p>
            </div>
        """.formatted(name, verifyUrl);

        Map<String, Object> body = Map.of(
                "from", from,
                "to", new String[]{toEmail},
                "subject", "Confirme ton adresse email — QuizAI",
                "html", html
        );

        webClient.post()
                .uri("/emails")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        res -> System.out.println("✅ Email envoyé à " + toEmail),
                        err -> {
                            // En dev : affiche le lien dans la console si l'envoi échoue
                            System.err.println("⚠️  Envoi email échoué (" + err.getMessage() + ")");
                            System.out.println("🔗 LIEN DE VÉRIFICATION (dev) : " + verifyUrl);
                        }
                );
    }
}
