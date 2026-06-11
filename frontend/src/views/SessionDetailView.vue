<template>
  <AppLayout>
    <div class="max-w-3xl mx-auto px-4 py-10 space-y-8">

      <div v-if="loading" class="text-gray-500 text-sm py-10 text-center">Chargement...</div>

      <template v-else-if="session">

        <!-- En-tête session -->
        <div class="bg-gray-800/50 border border-gray-700/50 rounded-xl px-6 py-6 space-y-4">
          <div class="flex items-start justify-between">
            <div class="space-y-1">
              <h1 class="text-xl font-black tracking-tight line-clamp-2">
                {{ session.subject || 'Quiz sans titre' }}
              </h1>
              <p class="text-gray-400 text-sm">
                {{ formatDate(session.createdAt) }} — Difficulté : {{ session.difficulty }}
              </p>
            </div>
            <div class="text-right">
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
              <span class="text-xs font-bold mt-0.5" :class="result.correct ? 'text-green-400' : 'text-red-400'">
                {{ result.correct ? '◈' : '◇' }}
              </span>
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
import { useRoute, RouterLink } from 'vue-router'
import AppLayout from '@/components/layout/AppLayout.vue'
import { getSessionDetail } from '@/services/sessionService'

const route = useRoute()
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
