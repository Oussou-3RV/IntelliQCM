package com.intelliqcm.backend.controller;

import com.intelliqcm.backend.model.*;
import com.intelliqcm.backend.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // POST /api/quiz/generate
    @PostMapping("/generate")
    public ResponseEntity<?> generateQuiz(@RequestBody QuizRequest request) {
        try {
            List<Question> questions = quizService.generateQuestions(request);
            return ResponseEntity.ok(questions);
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
}