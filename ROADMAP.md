# intelliQCM — Roadmap SaaS

##  Version actuelle (v1.0)
- [x] Upload cours PDF / texte
- [x] Génération de questions via OpenAI GPT-4o mini
- [x] Quiz question par question avec feedback immédiat
- [x] Rapport final avec conseil IA personnalisé
- [x] Choix du nombre de questions (5, 10, 15, 20)
- [x] Niveaux de difficulté (facile, moyen, difficile)

---

## 🚧 Version 2.0 — SaaS (en cours)

### 🌐 Landing Page
- [ ] Hero section avec présentation de l'app
- [ ] Section fonctionnalités
- [ ] Section tarifs (Gratuit / Premium)
- [ ] Call-to-action vers inscription
- [ ] Navbar avec liens Connexion / Inscription

### 🔐 Authentification
- [ ] Inscription (nom, email, mot de passe)
- [ ] Connexion avec JWT
- [ ] Protection des routes (Vue Router guards)
- [ ] Store Pinia pour gérer l'état auth
- [ ] Déconnexion

### 🗄️ Base de données PostgreSQL
- [ ] Entité User
- [ ] Entité QuizSession (date, score, difficulté, nb questions)
- [ ] Entité QuizResult (question, réponse choisie, correct)
- [ ] Relations JPA (User → QuizSession → QuizResult)
- [ ] Migration schema avec Spring Boot auto-ddl

### 📊 Tableau de bord personnel
- [ ] Nombre de quiz complétés
- [ ] Score moyen global
- [ ] Graphique de progression dans le temps
- [ ] Matières / thèmes les plus faibles

### 📚 Historique des quiz
- [ ] Liste de toutes les sessions passées
- [ ] Détail d'une session (questions, réponses, score)
- [ ] Filtrer par date / score / difficulté

### ⏱️ Mode examen
- [ ] Timer par question (configurable)
- [ ] Pas de feedback immédiat
- [ ] Résultat uniquement à la fin

### 💎 Modèle Freemium
- [ ] Gratuit : 3 quiz/jour, max 10 questions
- [ ] Premium : illimité, toutes fonctionnalités
- [ ] Compteur de quiz quotidien côté backend
- [ ] Page upgrade

### 📁 Bibliothèque de cours
- [ ] Sauvegarder un cours uploadé
- [ ] Réutiliser un cours sans re-uploader
- [ ] Supprimer un cours

---

## 🔮 Version 3.0 — Idées futures
- [ ] Support multilingue (FR / EN)
- [ ] Export PDF du rapport final
- [ ] Mode révision espacée (répétition des questions ratées)
- [ ] Partage de quiz entre utilisateurs
- [ ] Déploiement Railway (frontend + backend + PostgreSQL)

---

## Stack technique

| Couche | Technologie |
|--------|-------------|
| Frontend | Vue.js 3 + Tailwind CSS + Pinia + Vue Router |
| Backend | Spring Boot · Java 17 · Spring Security · JWT |
| Base de données | PostgreSQL (local Docker) |
| IA | OpenAI GPT-4o mini |
| Déploiement futur | Railway |

---

## Ordre de développement recommandé

1. Landing page
2. Auth (Inscription / Connexion / JWT)
3. PostgreSQL + entités JPA
4. Historique des sessions
5. Tableau de bord
6. Freemium
7. Mode examen
8. Bibliothèque de cours
