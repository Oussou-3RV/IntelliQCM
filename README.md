# IntelliQCM

IntelliQCM est une application web qui génère des questionnaires à choix multiples (QCM) à l'aide de l'intelligence artificielle. L'utilisateur crée un compte, demande un QCM et obtient des questions prêtes à utiliser, générées par le modèle GPT-4o mini d'OpenAI.


**Démo en ligne :** intelliQCM.com

## Fonctionnalités

- Inscription et connexion sécurisées (authentification par jeton JWT)
- Génération automatique de QCM à partir d'une demande de l'utilisateur
- Interface web simple à utiliser
- API REST séparée de l'interface

## Technologies utilisées

| Partie | Technologie |
|---|---|
| Frontend | Vue.js 3 |
| Backend | Spring Boot (Java) |
| Intelligence artificielle | API OpenAI (GPT-4o mini) |
| Authentification | JWT |
| Déploiement | Vercel (frontend), Render (backend) |

## Architecture

Le projet est séparé en deux parties qui communiquent par une API REST :

- **Le frontend (Vue.js 3)** affiche l'interface et envoie les requêtes au backend.
- **Le backend (Spring Boot)** gère les comptes, vérifie l'identité de l'utilisateur avec le JWT, appelle l'API d'OpenAI et renvoie les QCM générés.

Cette séparation permet de déployer et de faire évoluer chaque partie de façon indépendante.

## Lancer le projet en local

### Prérequis

- Java (version [à préciser]) et Maven
- Node.js et npm
- Une clé d'API OpenAI

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Adapte les noms de dossiers si ton dépôt est organisé autrement.

## Variables d'environnement

Les informations sensibles ne sont jamais écrites dans le code. Elles sont fournies par des variables d'environnement. Il faut définir au minimum :

| Variable | Rôle |
|---|---|
| `OPENAI_API_KEY` | Clé d'accès à l'API OpenAI |
| `JWT_SECRET` | Clé utilisée pour signer les jetons JWT |

Ajoute ici les variables liées à la base de données si ton projet en utilise une.

## Ce que j'ai appris

- l'authentification par JWT
- l'intégration d'une API d'intelligence artificielle dans une vraie application
- le déploiement en production et la gestion des secrets (clés d'API, configuration par environnement)
- un flux de travail Git propre, avec des commits clairs

## Pistes d'amélioration

- Ajouter des tests automatisés
- Permettre de sauvegarder et de partager les QCM
- Ajouter différents niveaux de difficulté

## Auteur

Ousmane Balde — étudiant au baccalauréat en informatique, Université du Québec à Trois-Rivières (UQTR).
