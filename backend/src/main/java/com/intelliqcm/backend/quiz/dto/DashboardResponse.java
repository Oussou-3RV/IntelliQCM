package com.intelliqcm.backend.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private long totalSessions;
    private int averageScore;
    private long quizzesToday;
    private Map<String, Integer> scoreByDifficulty;
    private List<ProgressPoint> recentProgress;

    @Data
    @AllArgsConstructor
    public static class ProgressPoint {
        private String date;
        private int scorePercent;
        private String difficulty;
    }
}
