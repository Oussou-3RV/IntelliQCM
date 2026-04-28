# QuizAI — Application de révision intelligente assistée par IA

Outil de révision personnalisé : uploade ton cours (PDF ou texte), l'IA génère un quiz à choix multiples, te pose les questions une par une avec feedback immédiat, et te donne un conseil personnalisé à la fin.

---

## Prérequis

- Java 17+
- Maven
- Node.js 18+
- Une clé API OpenAI

---

## Installation et lancement

### 1. Cloner le projet

```bash
git clone https://dmigit.uqtr.ca/baou1969/intelliqcm
cd intelliqcm
```

### 2. Backend (Spring Boot)

```bash
cd backend
```

Ouvre le fichier `src/main/resources/application.properties` et remplace :

```properties
openai.api.key=ta_clé_openai_ici
```

Lance le backend :

```bash
./mvnw spring-boot:run
```

Le serveur démarre sur `http://localhost:8080`

### 3. Frontend (Vue.js)

Dans un nouveau terminal :

```bash
cd frontend
npm install
npm run dev
```

L'application est disponible sur `http://localhost:5173`

---

## Utilisation

1. Ouvre `http://localhost:5173` dans ton navigateur
2. Upload ton cours en PDF ou colle ton texte
3. Choisis le nombre de questions et le niveau de difficulté
4. Clique sur **Générer le quiz**
5. Réponds aux questions une par une
6. Consulte ton rapport final et le conseil IA personnalisé

---
