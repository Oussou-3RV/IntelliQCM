package com.intelliqcm.backend.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SessionDetailResponse {
    private Long id;
    private int score;
    private int totalQuestions;
    private String difficulty;
    private String subject;
    private LocalDateTime createdAt;
    private List<ResultItemResponse> results;

    @Data
    @AllArgsConstructor
    public static class ResultItemResponse {
        private Long id;
        private String questionText;
        private String selectedAnswer;
        private String correctAnswer;
        private boolean correct;
    }
}
