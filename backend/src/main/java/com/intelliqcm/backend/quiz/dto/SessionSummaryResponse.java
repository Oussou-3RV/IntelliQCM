package com.intelliqcm.backend.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SessionSummaryResponse {
    private Long id;
    private int score;
    private int totalQuestions;
    private String difficulty;
    private String subject;
    private LocalDateTime createdAt;

    public int getScorePercent() {
        return totalQuestions > 0 ? (score * 100 / totalQuestions) : 0;
    }
}
