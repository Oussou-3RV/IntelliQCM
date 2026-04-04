package com.intelliqcm.backend.model;

import lombok.Data;
import java.util.List;

@Data
public class AdviceRequest {
    private List<QuizResult> results;  // toutes les réponses de l'étudiant
    private String difficulty;          // niveau de difficulté choisi
    private int totalQuestions;         // nombre total de questions
}
