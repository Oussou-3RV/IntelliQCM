<template>
  <AppLayout>
    <div class="max-w-3xl mx-auto px-4 py-10 space-y-8">

      <div v-if="loading" class="flex items-center justify-center gap-2 text-gray-500 text-sm py-10">
        <Loader2 :size="16" class="animate-spin" /> Chargement...
      </div>

      <template v-else-if="session">

        <!-- En-tête session -->
        <div class="bg-gray-800/50 border border-gray-700/50 rounded-xl px-6 py-6 space-y-4">
          <div class="flex items-start justify-between gap-4">
            <div class="space-y-1 min-w-0">
              <h1 class="text-xl font-black tracking-tight line-clamp-2">
                {{ session.subject || 'Quiz sans titre' }}
              </h1>
              <p class="text-gray-400 text-sm">
                {{ formatDate(session.createdAt) }} — Difficulté : {{ session.difficulty }}
              </p>
            </div>
            <div class="text-right shrink-0">
              <p class="text-3xl font-black" :class="scoreColor(scorePercent)">
                {{ scorePercent }}%
              </p>
              <p class="text-gray-500 text-sm">{{ session.score }} / {{ session.totalQuestions }}</p>
            </div>
          </div>

          <!-- Barre de score -->
          <div class="h-2 bg-gray-700 rounded-full overflow-hidden">
            <div
              class="h-full rounded-full transition-all duration-700"
              :class="scoreBarColor(scorePercent)"
              :style="{ width: scorePercent + '%' }"
            />
          </div>

          <!-- Bouton recommencer -->
          <div class="pt-2 flex flex-col sm:flex-row gap-3">
            <button
              @click="replayQuiz"
              class="flex items-center justify-center gap-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-semibold px-5 py-2.5 rounded-lg transition-colors"
            >
              <RotateCcw :size="15" />
              Recommencer ce quiz
            </button>
            <RouterLink
              to="/history"
              class="flex items-center justify-center gap-2 text-sm text-gray-400 hover:text-white border border-gray-700 hover:border-gray-600 px-5 py-2.5 rounded-lg transition-colors"
            >
              <ChevronLeft :size="15" />
              Retour à l'historique
            </RouterLink>
          </div>
        </div>

        <!-- Détail des questions -->
        <div class="space-y-4">
          <h2 class="text-sm font-semibold text-gray-400 uppercase tracking-widest">
            Détail des réponses
          </h2>
          <div
            v-for="(result, i) in session.results"
            :key="result.id"
            class="bg-gray-800/40 border rounded-xl px-5 py-4 space-y-2"
            :class="result.correct ? 'border-green-500/30' : 'border-red-500/30'"
          >
            <div class="flex items-start gap-3">
              <CheckCircle v-if="result.correct" :size="15" class="text-green-400 mt-0.5 shrink-0" />
              <XCircle v-else :size="15" class="text-red-400 mt-0.5 shrink-0" />
              <p class="text-white text-sm font-medium">{{ i + 1 }}. {{ result.questionText }}</p>
            </div>
            <div class="pl-6 space-y-1 text-sm">
              <p class="text-gray-400">
                Ta réponse :
                <span :class="result.correct ? 'text-green-400' : 'text-red-400'">
                  {{ result.selectedAnswer }}
                </span>
              </p>
              <p v-if="!result.correct" class="text-gray-400">
                Bonne réponse :
                <span class="text-green-400">{{ result.correctAnswer }}</span>
              </p>
            </div>
          </div>
        </div>

      </template>
    </div>
  </AppLayout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter, RouterLink } from 'vue-router'
import AppLayout from '@/components/layout/AppLayout.vue'
import { Loader2, RotateCcw, ChevronLeft, CheckCircle, XCircle } from 'lucide-vue-next'
import { getSessionDetail } from '@/services/sessionService'

const route = useRoute()
const router = useRouter()
const session = ref(null)
const loading = ref(true)

onMounted(async () => {
  try {
    session.value = await getSessionDetail(route.params.id)
  } finally {
    loading.value = false
  }
})

const scorePercent = computed(() =>
  session.value
    ? Math.round(session.value.score * 100 / session.value.totalQuestions)
    : 0
)

function replayQuiz() {
  sessionStorage.setItem('replayConfig', JSON.stringify({
    difficulty: session.value.difficulty,
    questionCount: session.value.totalQuestions,
    mode: session.value.mode || 'revision',
    timerSeconds: 30
  }))
  router.push('/app')
}

function formatDate(iso) {
  return new Date(iso).toLocaleDateString('fr-CA', {
    year: 'numeric', month: 'short', day: 'numeric',
    hour: '2-digit', minute: '2-digit'
  })
}

function scoreColor(p) {
  if (p >= 80) return 'text-green-400'
  if (p >= 50) return 'text-yellow-400'
  return 'text-red-400'
}

function scoreBarColor(p) {
  if (p >= 80) return 'bg-green-500'
  if (p >= 50) return 'bg-yellow-500'
  return 'bg-red-500'
}
</script>
