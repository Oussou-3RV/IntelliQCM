package com.intelliqcm.backend.quiz.service;

import com.intelliqcm.backend.auth.model.User;
import com.intelliqcm.backend.quiz.dto.SaveSessionRequest;
import com.intelliqcm.backend.quiz.dto.SessionDetailResponse;
import com.intelliqcm.backend.quiz.dto.SessionSummaryResponse;
import com.intelliqcm.backend.quiz.model.QuizResult;
import com.intelliqcm.backend.quiz.model.QuizSession;
import com.intelliqcm.backend.quiz.repository.QuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizSessionService {

    private final QuizSessionRepository sessionRepository;

    public SessionSummaryResponse saveSession(User user, SaveSessionRequest request) {
        QuizSession session = QuizSession.builder()
                .user(user)
                .score(request.getScore())
                .totalQuestions(request.getTotalQuestions())
                .difficulty(request.getDifficulty())
                .subject(request.getSubject())
                .mode(request.getMode() != null ? request.getMode() : "revision")
                .build();

        if (request.getResults() != null) {
            request.getResults().forEach(r -> {
                QuizResult result = QuizResult.builder()
                        .session(session)
                        .questionText(r.getQuestionText())
                        .selectedAnswer(r.getSelectedAnswer())
                        .correctAnswer(r.getCorrectAnswer())
                        .correct(r.isCorrect())
                        .build();
                session.getResults().add(result);
            });
        }

        QuizSession saved = sessionRepository.save(session);
        return toSummary(saved);
    }

    public List<SessionSummaryResponse> getUserSessions(Long userId) {
        return sessionRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toSummary)
                .toList();
    }

    public SessionDetailResponse getSessionDetail(Long sessionId, Long userId) {
        QuizSession session = sessionRepository.findById(sessionId)
                .filter(s -> s.getUser().getId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Session introuvable."));

        List<SessionDetailResponse.ResultItemResponse> results = session.getResults().stream()
                .map(r -> new SessionDetailResponse.ResultItemResponse(
                        r.getId(),
                        r.getQuestionText(),
                        r.getSelectedAnswer(),
                        r.getCorrectAnswer(),
                        r.isCorrect()
                ))
                .toList();

        return new SessionDetailResponse(
                session.getId(),
                session.getScore(),
                session.getTotalQuestions(),
                session.getDifficulty(),
                session.getSubject(),
                session.getCreatedAt(),
                results
        );
    }

    private SessionSummaryResponse toSummary(QuizSession s) {
        return new SessionSummaryResponse(
                s.getId(),
                s.getScore(),
                s.getTotalQuestions(),
                s.getDifficulty(),
                s.getSubject(),
                s.getCreatedAt()
        );
    }
}
