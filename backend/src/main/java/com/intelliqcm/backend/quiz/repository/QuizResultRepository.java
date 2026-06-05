package com.intelliqcm.backend.quiz.repository;

import com.intelliqcm.backend.quiz.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findBySessionId(Long sessionId);
}
