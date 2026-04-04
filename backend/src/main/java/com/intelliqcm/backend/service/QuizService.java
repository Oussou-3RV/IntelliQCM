package com.intelliqcm.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelliqcm.backend.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public QuizService(ObjectMapper objectMapper) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .build();
        this.objectMapper = objectMapper;
    }

    // ── Génération des questions ──────────────────────────────────────
    public List<Question> generateQuestions(QuizRequest request) throws Exception {

        String difficultyDesc = switch (request.getDifficulty()) {
            case "facile"    -> "simples et directes, portant sur les définitions et concepts de base";
            case "difficile" -> "complexes, demandant de l'analyse, de la synthèse et de la réflexion critique";
            default          -> "de niveau intermédiaire, demandant de la compréhension et de l'application";
        };

        String prompt = String.format("""
                Tu es un professeur expert. Génère exactement %d questions QCM à partir de ce cours.
                
                Niveau de difficulté : %s
                
                Cours :
                %s
                
                Réponds UNIQUEMENT avec un tableau JSON valide, sans texte avant ou après :
                [
                  {
                    "question": "...",
                    "choices": ["A. ...", "B. ...", "C. ...", "D. ..."],
                    "correct": 0,
                    "explanation": "Explication claire en 2-3 phrases."
                  }
                ]
                
                Le champ "correct" est l'index (0-3) de la bonne réponse dans "choices".
                """,
                request.getQuestionCount(),
                difficultyDesc,
                request.getCourseContent().substring(0, Math.min(request.getCourseContent().length(), 12000))
        );

        String raw = callOpenAI(prompt);
        String clean = raw.replaceAll("```json|```", "").trim();

        return objectMapper.readValue(
                clean,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Question.class)
        );
    }

    // ── Génération du conseil final ───────────────────────────────────
    public String generateAdvice(AdviceRequest request) throws Exception {

        long correctCount = request.getResults().stream()
                .filter(QuizResult::isCorrect)
                .count();

        int scorePercent = (int) Math.round((double) correctCount / request.getTotalQuestions() * 100);

        String missedQuestions = request.getResults().stream()
                .filter(r -> !r.isCorrect())
                .map(r -> "- " + r.getQuestion().getQuestion()
                        + " (bonne réponse: " + r.getQuestion().getChoices().get(r.getQuestion().getCorrect()) + ")")
                .reduce("", (a, b) -> a + "\n" + b);

        String prompt = missedQuestions.isBlank()
                ? String.format("""
                        Un étudiant a eu %d%% au quiz (difficulté: %s), il a tout bon !
                        Donne-lui un conseil pour maintenir ce niveau et aller encore plus loin.
                        3-4 phrases max, sois encourageant.
                        """, scorePercent, request.getDifficulty())
                : String.format("""
                        Un étudiant a eu %d%% au quiz (difficulté: %s).
                        Les questions ratées :
                        %s
                        
                        Donne un conseil de révision personnalisé en 3-4 phrases :
                        quels concepts retravailler, comment et dans quel ordre.
                        Sois direct et encourageant.
                        """, scorePercent, request.getDifficulty(), missedQuestions);

        return callOpenAI(prompt);
    }

    // ── Appel commun à l'API OpenAI ───────────────────────────────────
    private String callOpenAI(String prompt) {
        Map<String, Object> body = Map.of(
                "model", model,
                "max_tokens", 2000,
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        Map response = webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<Map> choices = (List<Map>) response.get("choices");
        Map message = (Map) choices.get(0).get("message");
        return (String) message.get("content");
    }
}