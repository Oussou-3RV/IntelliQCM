package com.intelliqcm.backend.model;

import lombok.Data;

@Data
public class QuizResult {
    private Question question;   // la question posée
    private int selected;        // index du choix de l'étudiant
    private boolean correct;     // bonne ou mauvaise réponse
}