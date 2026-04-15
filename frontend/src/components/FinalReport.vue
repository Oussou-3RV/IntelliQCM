<template>
    <div class="space-y-6 animate-fade-up">
  
      <!-- Score hero -->
      <div class="bg-gray-800/50 border border-gray-700 rounded-2xl p-6 flex items-center gap-6">
        <!-- Cercle SVG -->
        <div class="relative w-28 h-28 flex-shrink-0">
          <svg viewBox="0 0 100 100" class="w-full h-full -rotate-90">
            <circle cx="50" cy="50" r="42" fill="none" stroke="#1f2937" stroke-width="8"/>
            <circle
              cx="50" cy="50" r="42" fill="none"
              :stroke="scoreColor"
              stroke-width="8"
              stroke-linecap="round"
              :stroke-dasharray="`${scorePercent * 2.638} 263.8`"
              stroke-dashoffset="0"
              style="transition: stroke-dasharray 1s ease"
            />
          </svg>
          <div class="absolute inset-0 flex flex-col items-center justify-center">
            <span class="text-white font-black text-2xl leading-none">{{ correctCount }}</span>
            <span class="text-gray-400 text-sm">/ {{ results.length }}</span>
          </div>
        </div>
  
        <!-- Infos score -->
        <div>
          <h2 class="text-white font-bold text-xl mb-1">{{ scoreTitle }}</h2>
          <p class="text-gray-400">{{ scorePercent }}% de réussite</p>
          <p class="text-gray-500 text-sm mt-1">Difficulté : {{ config.difficulty }}</p>
        </div>
      </div>
  
      <!-- Conseil IA -->
      <div class="bg-gray-800/50 border border-gray-700 rounded-2xl p-6">
        <p class="text-indigo-400 text-xs uppercase tracking-widest font-semibold mb-3">
          🎯 Conseil personnalisé
        </p>
        <p v-if="!advice" class="text-gray-400 italic">
          Analyse en cours
          <span class="animate-pulse">...</span>
        </p>
        <p v-else class="text-gray-300 leading-relaxed">{{ advice }}</p>
      </div>
  
      <!-- Détail des réponses -->
      <div class="bg-gray-800/50 border border-gray-700 rounded-2xl p-6">
        <h3 class="text-white font-semibold mb-4">Détail des réponses</h3>
        <div class="space-y-3">
          <div
            v-for="(r, i) in results"
            :key="i"
            class="flex items-start gap-3 pb-3 border-b border-gray-700/50 last:border-none last:pb-0"
          >
            <span
              class="font-bold flex-shrink-0 mt-0.5"
              :class="r.correct ? 'text-green-400' : 'text-red-400'"
            >
              {{ r.correct ? '✓' : '✗' }}
            </span>
            <span class="text-gray-400 text-sm leading-relaxed">
              {{ r.question.question }}
            </span>
          </div>
        </div>
      </div>
  
      <!-- Bouton recommencer -->
      <div class="flex justify-center">
        <button
          class="px-10 py-3 bg-indigo-600 hover:bg-indigo-500 text-white font-semibold rounded-full transition-all duration-200 hover:shadow-lg hover:shadow-indigo-500/25"
          @click="$emit('restart')"
        >
          Recommencer ↺
        </button>
      </div>
  
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue'
  import { generateAdvice } from '../services/quizService.js'
  
  const props = defineProps({
    results: Array,
    config: Object
  })
  
  defineEmits(['restart'])
  
  const advice = ref('')
  
  const correctCount = computed(() =>
    props.results.filter(r => r.correct).length
  )
  
  const scorePercent = computed(() =>
    Math.round((correctCount.value / props.results.length) * 100)
  )
  
  const scoreColor = computed(() => {
    if (scorePercent.value >= 75) return '#43e97b'
    if (scorePercent.value >= 50) return '#f59e0b'
    return '#ff4d6d'
  })
  
  const scoreTitle = computed(() => {
    if (scorePercent.value >= 80) return 'Excellent travail ! 🎉'
    if (scorePercent.value >= 60) return 'Bon travail 👍'
    if (scorePercent.value >= 40) return 'Continue tes efforts 💪'
    return 'À revoir sérieusement 📚'
  })
  
  onMounted(async () => {
    try {
      advice.value = await generateAdvice(
        props.results,
        props.config.difficulty,
        props.results.length
      )
    } catch (e) {
      advice.value = "Impossible de générer le conseil pour le moment."
    }
  })
  </script>
  
  <style scoped>
  .animate-fade-up {
    animation: fadeUp 0.4s ease;
  }
  
  @keyframes fadeUp {
    from { opacity: 0; transform: translateY(20px); }
    to   { opacity: 1; transform: translateY(0); }
  }
  </style>