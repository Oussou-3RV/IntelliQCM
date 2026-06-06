<template>
    <div class="min-h-screen bg-gray-900 text-white">

      <!-- Header -->
      <header class="border-b border-gray-800 px-6 py-4">
        <div class="max-w-2xl mx-auto flex items-center justify-between">
          <div class="flex items-center gap-2">
            <span class="text-indigo-400 text-2xl">◈</span>
            <span class="text-white font-black text-xl tracking-tight">QuizAI</span>
          </div>
          <RouterLink to="/" class="text-sm text-gray-500 hover:text-gray-300 transition-colors">
            Accueil
          </RouterLink>
        </div>
      </header>

      <div class="max-w-2xl mx-auto px-4 py-12 space-y-8">
  
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
  
          <!-- Progress bar -->
          <div class="space-y-2">
            <div class="flex justify-between text-sm text-gray-400">
              <span>Question {{ currentIndex + 1 }} / {{ questions.length }}</span>
              <span>{{ score }} correcte(s)</span>
            </div>
            <div class="h-1.5 bg-gray-700 rounded-full overflow-hidden">
              <div
                class="h-full bg-gradient-to-r from-indigo-500 to-purple-500 rounded-full transition-all duration-500"
                :style="{ width: progressPercent + '%' }"
              />
            </div>
          </div>
  
          <QuestionCard
            v-if="!showFeedback"
            :question="currentQuestion"
            @answered="onAnswered"
          />
  
          <AnswerFeedback
            v-else
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
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue'
  import { RouterLink } from 'vue-router'
  import { generateQuiz } from '../services/quizService.js'
  import FileUploader from '../components/FileUploader.vue'
  import QuizConfig from '../components/QuizConfig.vue'
  import QuestionCard from '../components/QuestionCard.vue'
  import AnswerFeedback from '../components/AnswerFeedback.vue'
  import FinalReport from '../components/FinalReport.vue'
  
  // ── State ─────────────────────────────────────────────────────────────
  const step = ref('setup')
  const courseContent = ref('')
  const config = ref({ questionCount: 10, difficulty: 'moyen' })
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
      error.value = 'Erreur lors de la génération du quiz. Vérifie que le backend est lancé.'
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
    showFeedback.value = true
  }
  
  function nextQuestion() {
    showFeedback.value = false
    selectedAnswer.value = null
    if (currentIndex.value + 1 >= questions.value.length) {
      step.value = 'report'
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