<template>
  <AppLayout>
    <div class="max-w-3xl mx-auto px-4 py-10 space-y-6">

      <div class="space-y-1">
        <h1 class="text-2xl font-black tracking-tight">Historique</h1>
        <p class="text-gray-400 text-sm">Toutes tes sessions de révision</p>
      </div>

      <!-- Chargement -->
      <div v-if="loading" class="flex items-center justify-center gap-2 text-gray-500 text-sm py-10">
        <Loader2 :size="16" class="animate-spin" /> Chargement...
      </div>

      <!-- Vide -->
      <div v-else-if="sessions.length === 0" class="text-center py-16 space-y-3">
        <BookOpen :size="36" class="text-gray-700 mx-auto" />
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
          class="flex items-center justify-between bg-gray-800/50 border border-gray-700/50 hover:border-gray-600 rounded-xl px-5 py-4 transition-colors group"
        >
          <div class="flex items-center gap-3 min-w-0">
            <div class="p-2 rounded-lg bg-gray-700/50 shrink-0">
              <BookOpen :size="15" class="text-gray-400" />
            </div>
            <div class="min-w-0 space-y-0.5">
              <p class="text-white font-medium text-sm truncate">
                {{ session.subject || 'Quiz sans titre' }}
              </p>
              <p class="text-gray-500 text-xs">
                {{ formatDate(session.createdAt) }} — {{ session.difficulty }}
              </p>
            </div>
          </div>
          <div class="flex items-center gap-3 shrink-0">
            <div class="text-right">
              <p class="text-sm font-black" :class="scoreColor(session.scorePercent)">
                {{ session.scorePercent }}%
              </p>
              <p class="text-gray-500 text-xs">{{ session.score }}/{{ session.totalQuestions }}</p>
            </div>
            <ChevronRight :size="16" class="text-gray-600 group-hover:text-gray-400 transition-colors" />
          </div>
        </RouterLink>
      </div>

    </div>
  </AppLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import AppLayout from '@/components/layout/AppLayout.vue'
import { BookOpen, ChevronRight, Loader2 } from 'lucide-vue-next'
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
