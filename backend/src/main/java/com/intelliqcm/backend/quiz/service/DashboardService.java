package com.intelliqcm.backend.quiz.service;

import com.intelliqcm.backend.quiz.dto.DashboardResponse;
import com.intelliqcm.backend.quiz.repository.QuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final QuizSessionRepository sessionRepository;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd MMM");

    public DashboardResponse getDashboard(Long userId) {
        long total = sessionRepository.countByUserId(userId);

        Double avg = sessionRepository.findAverageScoreByUserId(userId);
        int averageScore = avg != null ? (int) Math.round(avg) : 0;

        long today = sessionRepository.countByUserIdAndCreatedAtAfter(
                userId, LocalDateTime.now().toLocalDate().atStartOfDay()
        );

        Map<String, Integer> scoreByDifficulty = new HashMap<>();
        sessionRepository.findAvgScoreByDifficultyForUser(userId).forEach(row -> {
            String difficulty = (String) row[0];
            int score = row[1] != null ? (int) Math.round((Double) row[1]) : 0;
            scoreByDifficulty.put(difficulty, score);
        });

        List<DashboardResponse.ProgressPoint> recentProgress = sessionRepository
                .findTop10ByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(0, 10))
                .stream()
                .map(s -> new DashboardResponse.ProgressPoint(
                        s.getCreatedAt().format(DATE_FMT),
                        s.getTotalQuestions() > 0
                                ? (int) Math.round(s.getScore() * 100.0 / s.getTotalQuestions())
                                : 0,
                        s.getDifficulty()
                ))
                .toList();

        return DashboardResponse.builder()
                .totalSessions(total)
                .averageScore(averageScore)
                .quizzesToday(today)
                .scoreByDifficulty(scoreByDifficulty)
                .recentProgress(recentProgress)
                .build();
    }
}
