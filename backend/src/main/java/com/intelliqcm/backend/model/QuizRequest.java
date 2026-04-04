package com.intelliqcm.backend.model;

import lombok.Data;

@Data
public class QuizRequest {
    private String courseContent;  // le texte du cours
    private int questionCount;     // 5, 10, 15 ou 20
    private String difficulty;     // "facile", "moyen", "difficile"
}