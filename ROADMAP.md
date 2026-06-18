# intelliQCM — Roadmap SaaS

## Version actuelle (v1.0) — Terminée
- [x] Upload cours PDF / texte
- [x] Génération de questions via OpenAI GPT-4o mini
- [x] Quiz question par question avec feedback immédiat
- [x] Rapport final avec conseil IA personnalisé
- [x] Choix du nombre de questions (5, 10, 15, 20)
- [x] Niveaux de difficulté (facile, moyen, difficile)

---

## Version 2.0 — SaaS — Terminée ✅

### Landing Page
- [x] Hero section avec stats et animations au scroll (Intersection Observer)
- [x] Section fonctionnalités avec icônes Lucide
- [x] Section tarifs (Gratuit / Premium)
- [x] Call-to-action vers inscription
- [x] Navbar sticky avec hamburger mobile
- [x] Footer avec liens
- [x] Double background (gray-950 / gray-900) avec orbes indigo animés

### Authentification
- [x] Inscription (nom, email, mot de passe) avec JWT
- [x] Connexion avec JWT
- [x] Protection des routes (Vue Router guards)
- [x] Store Pinia pour gérer l'état auth
- [x] Déconnexion
- [x] Connexion via Google OAuth2
- [x] Vérification email via Resend (lien de confirmation)
- [x] Blocage connexion si email non vérifié (bannière jaune)
- [x] Réinscription avec email non vérifié → renouvelle le token
- [x] Redesign Login / Register "carte premium" (glow, backdrop-blur, animations)

### Base de données PostgreSQL
- [x] Entité User (name, email, password, provider, verified, role)
- [x] Entité QuizSession (date, score, difficulté, nb questions, mode)
- [x] Entité QuizResult (question, réponse choisie, correct)
- [x] Relations JPA (User → QuizSession → QuizResult)
- [x] Migration schema avec Spring Boot auto-ddl

### Tableau de bord personnel
- [x] Nombre de quiz complétés
- [x] Score moyen global
- [x] Graphique de progression dans le temps
- [x] Matières / thèmes les plus faibles

### Historique des quiz
- [x] Liste de toutes les sessions passées
- [x] Détail d'une session (questions, réponses, score)

### Mode examen
- [x] Timer par question (configurable)
- [x] Pas de feedback immédiat
- [x] Résultat uniquement à la fin

### Modèle Freemium
- [x] Gratuit : 3 quiz/jour, max 10 questions
- [x] Premium : illimité, toutes fonctionnalités
- [x] Compteur de quiz quotidien côté backend
- [x] Page upgrade

### UI / Responsive
- [x] Icônes Lucide sur toutes les vues
- [x] Bottom navigation mobile (quiz, historique, dashboard)
- [x] Layout desktop avec nav latérale
- [x] Animations scroll-reveal sur la landing

---

## Version 2.1 — Déploiement (en cours 🚧)

### Infrastructure Railway
- [ ] Créer un projet Railway
- [ ] Ajouter un service PostgreSQL Railway
- [ ] Déployer le backend Spring Boot (variable d'env pour tous les secrets)
- [ ] Déployer le frontend Vue.js (build statique ou service Node)
- [ ] Configurer les variables d'environnement :
  - `SPRING_DATASOURCE_URL` / `_USERNAME` / `_PASSWORD`
  - `JWT_SECRET`
  - `GOOGLE_CLIENT_ID` / `GOOGLE_CLIENT_SECRET`
  - `RESEND_API_KEY`
  - `OPENAI_API_KEY`

### OAuth2 & Email en production
- [ ] Ajouter l'URL de production dans Google Cloud Console (Authorized redirect URIs)
- [ ] Vérifier un domaine personnalisé dans Resend (pour envoyer à n'importe quelle adresse)
- [ ] Mettre à jour les URLs hardcodées `localhost:8080` → variable d'env dans le frontend

### Corrections avant déploiement
- [ ] Remplacer `http://localhost:8080` par une variable d'env `VITE_API_URL` dans le frontend
- [ ] Remplacer `http://localhost:5173` par une variable d'env dans le backend (CORS + OAuth2 callback)

---

## Version 3.0 — Idées futures
- [ ] Bibliothèque de cours (sauvegarder / réutiliser un cours sans re-uploader)
- [ ] Support multilingue (FR / EN)
- [ ] Export PDF du rapport final
- [ ] Mode révision espacée (répétition des questions ratées)
- [ ] Partage de quiz entre utilisateurs
- [ ] Replay d'une session passée

---

## Stack technique

| Couche | Technologie |
|--------|-------------|
| Frontend | Vue.js 3 + Tailwind CSS + Pinia + Vue Router + Lucide |
| Backend | Spring Boot 4 · Java 17 · Spring Security · JWT · WebFlux |
| Base de données | PostgreSQL |
| Auth | JWT + Google OAuth2 (spring-boot-starter-oauth2-client) |
| Email | Resend API |
| IA | OpenAI GPT-4o mini |
| Déploiement | Railway (backend + frontend + PostgreSQL) |
