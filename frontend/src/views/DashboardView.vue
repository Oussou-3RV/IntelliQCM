<template>
  <div class="min-h-screen bg-gray-900 text-white">

    <header class="border-b border-gray-800 px-6 py-4">
      <div class="max-w-4xl mx-auto flex items-center justify-between">
        <div class="flex items-center gap-2">
          <span class="text-indigo-400 text-2xl">◈</span>
          <span class="text-white font-black text-xl tracking-tight">QuizAI</span>
        </div>
        <nav class="flex items-center gap-6 text-sm text-gray-400">
          <RouterLink to="/app" class="hover:text-white transition-colors">Nouveau quiz</RouterLink>
          <RouterLink to="/history" class="hover:text-white transition-colors">Historique</RouterLink>
          <RouterLink to="/dashboard" class="text-white font-medium">Tableau de bord</RouterLink>
        </nav>
      </div>
    </header>

    <div class="max-w-4xl mx-auto px-4 py-10 space-y-8">

      <div class="space-y-1">
        <h1 class="text-2xl font-black tracking-tight">Tableau de bord</h1>
        <p class="text-gray-400 text-sm">Bonjour {{ auth.user?.name }} — voici ta progression</p>
      </div>

      <div v-if="loading" class="text-gray-500 text-sm py-10 text-center">Chargement...</div>

      <template v-else-if="data">

        <!-- Cartes stats -->
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div
            v-for="stat in stats"
            :key="stat.label"
            class="bg-gray-800/50 border border-gray-700/50 rounded-xl px-5 py-5 space-y-1"
          >
            <p class="text-gray-400 text-xs uppercase tracking-widest">{{ stat.label }}</p>
            <p class="text-2xl font-black" :class="stat.color">{{ stat.value }}</p>
          </div>
        </div>

        <!-- Score par difficulté -->
        <div class="bg-gray-800/50 border border-gray-700/50 rounded-xl px-6 py-6 space-y-4">
          <h2 class="text-sm font-semibold text-gray-400 uppercase tracking-widest">Score par difficulté</h2>
          <div v-if="Object.keys(data.scoreByDifficulty).length === 0" class="text-gray-500 text-sm">
            Pas encore assez de données.
          </div>
          <div v-else class="space-y-3">
            <div
              v-for="(score, difficulty) in data.scoreByDifficulty"
              :key="difficulty"
              class="space-y-1"
            >
              <div class="flex justify-between text-sm">
                <span class="text-gray-300 capitalize">{{ difficulty }}</span>
                <span class="font-semibold" :class="scoreColor(score)">{{ score }}%</span>
              </div>
              <div class="h-2 bg-gray-700 rounded-full overflow-hidden">
                <div
                  class="h-full rounded-full transition-all duration-700"
                  :class="scoreBarColor(score)"
                  :style="{ width: score + '%' }"
                />
              </div>
            </div>
          </div>
        </div>

        <!-- Graphique progression récente -->
        <div class="bg-gray-800/50 border border-gray-700/50 rounded-xl px-6 py-6 space-y-4">
          <h2 class="text-sm font-semibold text-gray-400 uppercase tracking-widest">Progression récente</h2>
          <div v-if="data.recentProgress.length === 0" class="text-gray-500 text-sm">
            Complète ton premier quiz pour voir ta progression.
          </div>
          <div v-else class="space-y-2">
            <div
              v-for="(point, i) in [...data.recentProgress].reverse()"
              :key="i"
              class="flex items-center gap-4"
            >
              <span class="text-gray-500 text-xs w-14 shrink-0">{{ point.date }}</span>
              <div class="flex-1 h-2 bg-gray-700 rounded-full overflow-hidden">
                <div
                  class="h-full rounded-full transition-all duration-500"
                  :class="scoreBarColor(point.scorePercent)"
                  :style="{ width: point.scorePercent + '%' }"
                />
              </div>
              <span class="text-xs font-semibold w-10 text-right" :class="scoreColor(point.scorePercent)">
                {{ point.scorePercent }}%
              </span>
              <span class="text-xs text-gray-600 w-14">{{ point.difficulty }}</span>
            </div>
          </div>
        </div>

        <!-- Raccourcis -->
        <div class="grid md:grid-cols-2 gap-4">
          <RouterLink
            to="/app"
            class="bg-indigo-600/20 border border-indigo-500/30 hover:border-indigo-500/60 rounded-xl px-6 py-5 transition-colors space-y-1"
          >
            <p class="text-indigo-400 font-semibold">Nouveau quiz</p>
            <p class="text-gray-400 text-sm">Continuer à réviser avec l'IA</p>
          </RouterLink>
          <RouterLink
            to="/history"
            class="bg-gray-800/50 border border-gray-700/50 hover:border-gray-600 rounded-xl px-6 py-5 transition-colors space-y-1"
          >
            <p class="text-white font-semibold">Voir l'historique</p>
            <p class="text-gray-400 text-sm">Revoir toutes tes sessions passées</p>
          </RouterLink>
        </div>

      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { getDashboard } from '@/services/dashboardService'

const auth = useAuthStore()
const data = ref(null)
const loading = ref(true)

onMounted(async () => {
  try {
    data.value = await getDashboard()
  } finally {
    loading.value = false
  }
})

const stats = computed(() => {
  if (!data.value) return []
  return [
    { label: 'Quiz complétés', value: data.value.totalSessions, color: 'text-white' },
    { label: 'Score moyen', value: data.value.averageScore + '%', color: scoreColor(data.value.averageScore) },
    { label: "Aujourd'hui", value: data.value.quizzesToday, color: 'text-indigo-400' },
    {
      label: 'Meilleure diff.',
      value: bestDifficulty.value || '—',
      color: 'text-green-400'
    }
  ]
})

const bestDifficulty = computed(() => {
  if (!data.value) return null
  const entries = Object.entries(data.value.scoreByDifficulty)
  if (entries.length === 0) return null
  return entries.reduce((best, curr) => curr[1] > best[1] ? curr : best)[0]
})

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
