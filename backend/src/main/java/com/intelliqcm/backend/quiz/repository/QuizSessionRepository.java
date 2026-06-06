package com.intelliqcm.backend.quiz.repository;

import com.intelliqcm.backend.quiz.model.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {

    List<QuizSession> findByUserIdOrderByCreatedAtDesc(Long userId);

    long countByUserId(Long userId);

    long countByUserIdAndCreatedAtAfter(Long userId, LocalDateTime since);

    @Query("SELECT AVG(s.score * 100.0 / s.totalQuestions) FROM QuizSession s WHERE s.user.id = :userId")
    Double findAverageScoreByUserId(Long userId);

    @Query("SELECT s.difficulty, AVG(s.score * 100.0 / s.totalQuestions) FROM QuizSession s WHERE s.user.id = :userId GROUP BY s.difficulty")
    List<Object[]> findAvgScoreByDifficultyForUser(Long userId);

    @Query("SELECT s FROM QuizSession s WHERE s.user.id = :userId ORDER BY s.createdAt DESC")
    List<QuizSession> findTop10ByUserIdOrderByCreatedAtDesc(Long userId, org.springframework.data.domain.Pageable pageable);
}
