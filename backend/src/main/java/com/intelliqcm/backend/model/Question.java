package com.intelliqcm.backend.model;

import lombok.Data;
import java.util.List;

@Data
public class Question {
    private String question;       // l'énoncé
    private List<String> choices;  // 4 choix
    private int correct;           // index 0-3 de la bonne réponse
    private String explanation;    // explication de la bonne réponse
}