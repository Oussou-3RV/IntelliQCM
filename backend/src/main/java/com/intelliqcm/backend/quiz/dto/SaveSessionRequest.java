package com.intelliqcm.backend.quiz.dto;

import lombok.Data;
import java.util.List;

@Data
public class SaveSessionRequest {
    private int score;
    private int totalQuestions;
    private String difficulty;
    private String subject;
    private String mode;
    private List<ResultItem> results;

    @Data
    public static class ResultItem {
        private String questionText;
        private String selectedAnswer;
        private String correctAnswer;
        private boolean correct;
    }
}
