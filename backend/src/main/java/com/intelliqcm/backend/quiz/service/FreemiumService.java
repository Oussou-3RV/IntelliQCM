package com.intelliqcm.backend.quiz.service;

import com.intelliqcm.backend.auth.model.Role;
import com.intelliqcm.backend.auth.model.User;
import com.intelliqcm.backend.quiz.repository.QuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FreemiumService {

    private static final int FREE_DAILY_LIMIT = 3;
    private static final int FREE_MAX_QUESTIONS = 10;

    private final QuizSessionRepository sessionRepository;

    public void checkQuizAllowed(User user, int requestedQuestions) {
        if (user.getRole() == Role.PREMIUM) return;

        long todayCount = sessionRepository.countByUserIdAndCreatedAtAfter(
                user.getId(),
                LocalDateTime.now().toLocalDate().atStartOfDay()
        );

        if (todayCount >= FREE_DAILY_LIMIT) {
            throw new QuotaExceededException(
                "Tu as atteint la limite de " + FREE_DAILY_LIMIT + " quiz par jour. Passe en Premium pour continuer."
            );
        }

        if (requestedQuestions > FREE_MAX_QUESTIONS) {
            throw new QuotaExceededException(
                "Le plan gratuit est limité à " + FREE_MAX_QUESTIONS + " questions par quiz."
            );
        }
    }

    public long getRemainingQuizzesToday(User user) {
        if (user.getRole() == Role.PREMIUM) return -1; // illimité
        long used = sessionRepository.countByUserIdAndCreatedAtAfter(
                user.getId(),
                LocalDateTime.now().toLocalDate().atStartOfDay()
        );
        return Math.max(0, FREE_DAILY_LIMIT - used);
    }

    public static class QuotaExceededException extends RuntimeException {
        public QuotaExceededException(String message) {
            super(message);
        }
    }
}
