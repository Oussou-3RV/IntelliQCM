package com.intelliqcm.backend.controller;

import com.intelliqcm.backend.auth.model.User;
import com.intelliqcm.backend.model.*;
import com.intelliqcm.backend.quiz.service.FreemiumService;
import com.intelliqcm.backend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final FreemiumService freemiumService;

    // POST /api/quiz/generate
    @PostMapping("/generate")
    public ResponseEntity<?> generateQuiz(
            @RequestBody QuizRequest request,
            @AuthenticationPrincipal User user
    ) {
        try {
            if (user != null) {
                freemiumService.checkQuizAllowed(user, request.getQuestionCount());
            }
            List<Question> questions = quizService.generateQuestions(request);
            return ResponseEntity.ok(questions);
        } catch (FreemiumService.QuotaExceededException e) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(Map.of("message", e.getMessage(), "quota", true));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lors de la génération : " + e.getMessage());
        }
    }

    // POST /api/quiz/advice
    @PostMapping("/advice")
    public ResponseEntity<?> getAdvice(@RequestBody AdviceRequest request) {
        try {
            String advice = quizService.generateAdvice(request);
            return ResponseEntity.ok(advice);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lors de la génération du conseil : " + e.getMessage());
        }
    }

    // GET /api/quiz/quota — quota restant pour l'utilisateur connecté
    @GetMapping("/quota")
    public ResponseEntity<?> getQuota(@AuthenticationPrincipal User user) {
        if (user == null) return ResponseEntity.ok(Map.of("remaining", 3, "isPremium", false));
        long remaining = freemiumService.getRemainingQuizzesToday(user);
        boolean isPremium = user.getRole().name().equals("PREMIUM");
        return ResponseEntity.ok(Map.of("remaining", remaining, "isPremium", isPremium));
    }
}