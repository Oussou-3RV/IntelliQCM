package com.intelliqcm.backend.quiz.controller;

import com.intelliqcm.backend.auth.model.User;
import com.intelliqcm.backend.quiz.dto.SaveSessionRequest;
import com.intelliqcm.backend.quiz.dto.SessionDetailResponse;
import com.intelliqcm.backend.quiz.dto.SessionSummaryResponse;
import com.intelliqcm.backend.quiz.service.QuizSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final QuizSessionService sessionService;

    // POST /api/sessions — sauvegarder une session après le quiz
    @PostMapping
    public ResponseEntity<SessionSummaryResponse> save(
            @AuthenticationPrincipal User user,
            @RequestBody SaveSessionRequest request
    ) {
        return ResponseEntity.ok(sessionService.saveSession(user, request));
    }

    // GET /api/sessions — historique de l'utilisateur connecté
    @GetMapping
    public ResponseEntity<List<SessionSummaryResponse>> getHistory(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(sessionService.getUserSessions(user.getId()));
    }

    // GET /api/sessions/{id} — détail d'une session
    @GetMapping("/{id}")
    public ResponseEntity<SessionDetailResponse> getDetail(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(sessionService.getSessionDetail(id, user.getId()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleError(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
}
