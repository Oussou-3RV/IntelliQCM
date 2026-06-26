<template>
    <AppLayout>
      <div class="max-w-2xl mx-auto px-4 py-10 space-y-8">
  
        <!-- ÉTAPE 1 : Setup -->
        <div v-if="step === 'setup'" class="space-y-6">
          <div class="text-center space-y-3">
            <h1 class="text-4xl font-black tracking-tight">
              Révise avec l'<span class="text-indigo-400">Intelligence Artificielle</span>
            </h1>
            <p class="text-gray-400 text-lg">
              Upload ton cours, l'IA génère un quiz personnalisé et t'aide à cibler tes lacunes.
            </p>
          </div>

          <!-- Bandeau replay -->
          <div
            v-if="isReplay"
            class="flex items-center gap-3 bg-indigo-950/60 border border-indigo-500/30 rounded-lg px-4 py-3 text-sm"
          >
            <RotateCcw :size="15" class="text-indigo-400 shrink-0" />
            <span class="text-gray-300">
              Paramètres du quiz précédent restaurés —
              <span class="text-white font-medium">{{ config.difficulty }}, {{ config.questionCount }} questions, mode {{ config.mode }}</span>.
              Upload ton cours pour relancer.
            </span>
          </div>

          <!-- Bandeau quota -->
          <div
            v-if="auth.isAuthenticated && !auth.isPremium && quota !== null"
            class="flex items-center justify-between bg-gray-800/60 border border-gray-700/50 rounded-lg px-4 py-3 text-sm"
          >
            <span class="text-gray-400">
              Quiz restants aujourd'hui :
              <span class="font-semibold" :class="quota === 0 ? 'text-red-400' : 'text-white'">
                {{ quota }} / 3
              </span>
            </span>
            <RouterLink to="/upgrade" class="text-indigo-400 hover:text-indigo-300 transition-colors font-medium">
              Passer Premium
            </RouterLink>
          </div>

          <FileUploader @content-ready="onContentReady" />


          <QuizConfig
            v-if="courseContent"
            v-model="config"
            :loading="loading"
            @start="startQuiz"
          />

          <p v-if="error" class="text-red-400 text-sm text-center">{{ error }}</p>
        </div>
  
        <!-- ÉTAPE 2 : Quiz -->
        <div v-else-if="step === 'quiz'" class="space-y-6">

          <!-- Badge mode examen -->
          <div v-if="isExamMode" class="flex items-center justify-center">
            <span class="text-xs font-semibold uppercase tracking-widest border border-gray-600 text-gray-400 px-3 py-1 rounded-full">
              Mode examen — résultats à la fin
            </span>
          </div>

          <!-- Progress bar -->
          <div class="space-y-2">
            <div class="flex justify-between text-sm text-gray-400">
              <span>Question {{ currentIndex + 1 }} / {{ questions.length }}</span>
              <span v-if="!isExamMode">{{ score }} correcte(s)</span>
            </div>
            <div class="h-1.5 bg-gray-700 rounded-full overflow-hidden">
              <div
                class="h-full bg-gradient-to-r from-indigo-500 to-purple-500 rounded-full transition-all duration-500"
                :style="{ width: progressPercent + '%' }"
              />
            </div>
          </div>

          <!-- Timer (mode examen) -->
          <ExamTimer
            v-if="isExamMode && !showFeedback"
            :key="currentIndex"
            :seconds="config.timerSeconds || 30"
            @timeout="onTimeout"
          />

          <QuestionCard
            v-if="!showFeedback"
            :question="currentQuestion"
            @answered="onAnswered"
          />

          <!-- Feedback uniquement en mode révision -->
          <AnswerFeedback
            v-if="showFeedback && !isExamMode"
            :question="currentQuestion"
            :selected="selectedAnswer"
            :is-correct="isCorrect"
            @next="nextQuestion"
          />

        </div>
  
        <!-- ÉTAPE 3 : Rapport -->
        <div v-else-if="step === 'report'">
          <FinalReport
            :results="results"
            :config="config"
            @restart="restart"
          />
        </div>
  
      </div>
    </AppLayout>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue'
  import { RouterLink, useRouter } from 'vue-router'
  import { generateQuiz } from '../services/quizService.js'
  import { saveSession } from '../services/sessionService.js'
  import { useAuthStore } from '@/stores/auth'
  import ExamTimer from '../components/ExamTimer.vue'
  import { RotateCcw } from 'lucide-vue-next'

  import AppLayout from '../components/layout/AppLayout.vue'
  import FileUploader from '../components/FileUploader.vue'
  import QuizConfig from '../components/QuizConfig.vue'
  import QuestionCard from '../components/QuestionCard.vue'
  import AnswerFeedback from '../components/AnswerFeedback.vue'
  import FinalReport from '../components/FinalReport.vue'

  const auth = useAuthStore()
  const router = useRouter()
  const quota = ref(null)
  const isReplay = ref(false)

  onMounted(async () => {
    // Paramètres pré-remplis depuis l'historique (replay)
    const replay = sessionStorage.getItem('replayConfig')
    if (replay) {
      config.value = { ...config.value, ...JSON.parse(replay) }
      sessionStorage.removeItem('replayConfig')
      isReplay.value = true
    }

    if (auth.isAuthenticated) {
      try {
        const res = await fetch(`${import.meta.env.VITE_API_URL}/api/quiz/quota`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        })
        const data = await res.json()
        quota.value = data.isPremium ? null : Number(data.remaining)
      } catch {
        // silencieux si le backend n'est pas disponible
      }
    }
  })

  // ── State ─────────────────────────────────────────────────────────────
  const step = ref('setup')
  const courseContent = ref('')
  const config = ref({ questionCount: 10, difficulty: 'moyen', mode: 'revision', timerSeconds: 30 })
  const loading = ref(false)
  const error = ref('')
  
  const questions = ref([])
  const currentIndex = ref(0)
  const selectedAnswer = ref(null)
  const showFeedback = ref(false)
  const isCorrect = ref(false)
  const score = ref(0)
  const results = ref([])
  
  // ── Computed ──────────────────────────────────────────────────────────
  const currentQuestion = computed(() => questions.value[currentIndex.value])
  const progressPercent = computed(() =>
    Math.round((currentIndex.value / questions.value.length) * 100)
  )
  const isExamMode = computed(() => config.value.mode === 'exam')
  
  // ── Handlers ──────────────────────────────────────────────────────────
  function onContentReady(content) {
    courseContent.value = content
    error.value = ''
  }
  
  async function startQuiz() {
    loading.value = true
    error.value = ''
    try {
      questions.value = await generateQuiz(
        courseContent.value,
        config.value.questionCount,
        config.value.difficulty
      )
      currentIndex.value = 0
      score.value = 0
      results.value = []
      step.value = 'quiz'
    } catch (e) {
      if (e.status === 429 || e.message?.includes('quota')) {
        router.push('/upgrade')
        return
      } // au cas le backend ne demarre pas correctement msg d'erreur
      error.value = 'Impossible de générer le quiz pour le moment. Veuillez réessayer plus tard. Si le problème persiste, contactez le développeur.';
    } finally {
      loading.value = false
    }
  }
  
  function onAnswered(choiceIndex) {
    selectedAnswer.value = choiceIndex
    isCorrect.value = choiceIndex === currentQuestion.value.correct
    if (isCorrect.value) score.value++
    results.value.push({
      question: currentQuestion.value,
      selected: choiceIndex,
      correct: isCorrect.value
    })
    if (isExamMode.value) {
      // Pas de feedback — on passe directement à la question suivante
      nextQuestion()
    } else {
      showFeedback.value = true
    }
  }

  // Timeout timer — réponse marquée incorrecte automatiquement
  function onTimeout() {
    if (showFeedback.value) return
    results.value.push({
      question: currentQuestion.value,
      selected: null,
      correct: false
    })
    nextQuestion()
  }
  
  async function nextQuestion() {
    showFeedback.value = false
    selectedAnswer.value = null
    if (currentIndex.value + 1 >= questions.value.length) {
      step.value = 'report'
      if (auth.isAuthenticated) {
        try {
          await saveSession({
            score: score.value,
            totalQuestions: questions.value.length,
            difficulty: config.value.difficulty,
            mode: config.value.mode,
            subject: courseContent.value.slice(0, 100),
            results: results.value.map(r => ({
              questionText: r.question.question,
              selectedAnswer: String(r.question.choices[r.selected]),
              correctAnswer: String(r.question.choices[r.question.correct]),
              correct: r.correct
            }))
          })
        } catch (e) {
          console.warn('Session non sauvegardée :', e.message)
        }
      }
    } else {
      currentIndex.value++
    }
  }
  
  function restart() {
    step.value = 'setup'
    courseContent.value = ''
    questions.value = []
    results.value = []
    score.value = 0
  }
  </script>