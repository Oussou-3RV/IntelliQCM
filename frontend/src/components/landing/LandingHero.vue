<template>
  <section class="px-6 py-24 text-center overflow-hidden">
    <div class="max-w-3xl mx-auto space-y-8">

      <div class="space-y-4 animate-hero-up">
        <span class="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-widest text-indigo-400 border border-indigo-400/30 px-3 py-1 rounded-full">
          <Sparkles :size="12" />
          Révision assistée par IA
        </span>
        <h1 class="text-5xl font-black tracking-tight leading-tight text-white mt-4">
          Transforme ton cours<br />
          en quiz en <span class="text-indigo-400">30 secondes</span>
        </h1>
        <p class="text-gray-400 text-lg max-w-xl mx-auto leading-relaxed">
          Upload un PDF ou colle ton texte. QuizAI génère des questions ciblées,
          t'accompagne pendant la révision et identifie tes lacunes.
        </p>
      </div>

      <div class="flex flex-col sm:flex-row items-center justify-center gap-4 animate-hero-up" style="animation-delay: 150ms">
        <RouterLink
          to="/register"
          class="w-full sm:w-auto flex items-center justify-center gap-2 bg-indigo-600 hover:bg-indigo-500 text-white font-semibold px-8 py-3 rounded-lg transition-all hover:scale-[1.02] hover:shadow-lg hover:shadow-indigo-500/25"
        >
          Essayer gratuitement
          <ArrowRight :size="16" />
        </RouterLink>
        <RouterLink
          to="/app"
          class="w-full sm:w-auto flex items-center justify-center gap-2 text-gray-400 hover:text-white font-medium px-8 py-3 rounded-lg border border-gray-700 hover:border-gray-600 transition-colors"
        >
          <Play :size="14" />
          Voir la démo
        </RouterLink>
      </div>

      <p class="text-xs text-gray-500 animate-hero-up" style="animation-delay: 250ms">
        Gratuit — 3 quiz par jour sans inscription
      </p>

      <!-- Stats rapides -->
      <div class="grid grid-cols-3 gap-4 pt-4 border-t border-gray-800 animate-hero-up" style="animation-delay: 350ms">
        <div v-for="stat in stats" :key="stat.label" class="space-y-1">
          <p class="text-2xl font-black text-white">{{ stat.value }}</p>
          <p class="text-xs text-gray-500">{{ stat.label }}</p>
        </div>
      </div>

    </div>
  </section>
</template>

<script setup>
import { RouterLink } from 'vue-router'
import { Sparkles, ArrowRight, Play } from 'lucide-vue-next'

const stats = [
  { value: 'GPT-4o', label: 'Modèle IA' },
  { value: '< 30s',  label: 'Génération' },
  { value: '100%',   label: 'Personnalisé' },
]
</script>

<style scoped>
@keyframes heroUp {
  from { opacity: 0; transform: translateY(24px); }
  to   { opacity: 1; transform: translateY(0); }
}

.animate-hero-up {
  opacity: 0;
  animation: heroUp 0.6s ease forwards;
}
</style>
