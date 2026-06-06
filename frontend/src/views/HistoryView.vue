<template>
  <div class="min-h-screen bg-gray-900 text-white">

    <header class="border-b border-gray-800 px-6 py-4">
      <div class="max-w-3xl mx-auto flex items-center justify-between">
        <div class="flex items-center gap-2">
          <span class="text-indigo-400 text-2xl">◈</span>
          <span class="text-white font-black text-xl tracking-tight">QuizAI</span>
        </div>
        <div class="flex items-center gap-6 text-sm text-gray-400">
          <RouterLink to="/app" class="hover:text-white transition-colors">Nouveau quiz</RouterLink>
          <RouterLink to="/history" class="text-white font-medium">Historique</RouterLink>
          <RouterLink to="/dashboard" class="hover:text-white transition-colors">Tableau de bord</RouterLink>
        </div>
      </div>
    </header>

    <div class="max-w-3xl mx-auto px-4 py-10 space-y-6">

      <div class="space-y-1">
        <h1 class="text-2xl font-black tracking-tight">Historique</h1>
        <p class="text-gray-400 text-sm">Toutes tes sessions de révision</p>
      </div>

      <!-- Chargement -->
      <div v-if="loading" class="text-gray-500 text-sm py-10 text-center">
        Chargement...
      </div>

      <!-- Vide -->
      <div v-else-if="sessions.length === 0" class="text-center py-16 space-y-3">
        <p class="text-gray-400">Aucune session pour l'instant.</p>
        <RouterLink to="/app" class="text-indigo-400 hover:text-indigo-300 text-sm transition-colors">
          Faire mon premier quiz
        </RouterLink>
      </div>

      <!-- Liste -->
      <div v-else class="space-y-3">
        <RouterLink
          v-for="session in sessions"
          :key="session.id"
          :to="`/history/${session.id}`"
          class="block bg-gray-800/50 border border-gray-700/50 hover:border-gray-600 rounded-xl px-6 py-5 transition-colors"
        >
          <div class="flex items-center justify-between">
            <div class="space-y-1">
              <p class="text-white font-medium text-sm line-clamp-1">
                {{ session.subject || 'Quiz sans titre' }}
              </p>
              <p class="text-gray-500 text-xs">
                {{ formatDate(session.createdAt) }} — {{ session.difficulty }}
              </p>
            </div>
            <div class="text-right space-y-1">
              <p
                class="text-lg font-black"
                :class="scoreColor(session.scorePercent)"
              >
                {{ session.scorePercent }}%
              </p>
              <p class="text-gray-500 text-xs">
                {{ session.score }} / {{ session.totalQuestions }}
              </p>
            </div>
          </div>
        </RouterLink>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { getSessions } from '@/services/sessionService'

const sessions = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    sessions.value = await getSessions()
  } finally {
    loading.value = false
  }
})

function formatDate(iso) {
  return new Date(iso).toLocaleDateString('fr-CA', {
    year: 'numeric', month: 'short', day: 'numeric',
    hour: '2-digit', minute: '2-digit'
  })
}

function scoreColor(percent) {
  if (percent >= 80) return 'text-green-400'
  if (percent >= 50) return 'text-yellow-400'
  return 'text-red-400'
}
</script>
