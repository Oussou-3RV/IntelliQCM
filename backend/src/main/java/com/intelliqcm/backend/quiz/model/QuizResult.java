package com.intelliqcm.backend.quiz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_results")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private QuizSession session;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String selectedAnswer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(nullable = false)
    private boolean correct;
}
