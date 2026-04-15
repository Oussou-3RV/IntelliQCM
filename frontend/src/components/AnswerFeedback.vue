<template>
    <div class="space-y-4 animate-fade-up">
  
      <!-- Bannière correct / incorrect -->
      <div
        class="flex items-center gap-4 border rounded-2xl p-5"
        :class="isCorrect
          ? 'border-green-500/50 bg-green-500/10'
          : 'border-red-500/50 bg-red-500/10'"
      >
        <div
          class="w-12 h-12 rounded-full flex items-center justify-center text-xl font-bold flex-shrink-0"
          :class="isCorrect ? 'bg-green-500/20 text-green-400' : 'bg-red-500/20 text-red-400'"
        >
          {{ isCorrect ? '✓' : '✗' }}
        </div>
        <div>
          <p
            class="font-bold text-lg"
            :class="isCorrect ? 'text-green-400' : 'text-red-400'"
          >
            {{ isCorrect ? 'Bonne réponse !' : 'Pas tout à fait…' }}
          </p>
          <p v-if="!isCorrect" class="text-gray-300 text-sm mt-1">
            La bonne réponse était :
            <strong class="text-white">
              {{ question.choices[question.correct].replace(/^[A-D]\.\s*/, '') }}
            </strong>
          </p>
        </div>
      </div>
  
      <!-- Explication -->
      <div class="bg-gray-800/50 border border-gray-700 rounded-2xl p-5">
        <p class="text-indigo-400 text-xs uppercase tracking-widest font-semibold mb-3">
          💡 Explication
        </p>
        <p class="text-gray-300 leading-relaxed">
          {{ question.explanation }}
        </p>
      </div>
  
      <!-- Bouton continuer -->
      <div class="flex justify-end">
        <button
          class="px-8 py-3 bg-indigo-600 hover:bg-indigo-500 text-white font-semibold rounded-full transition-all duration-200 hover:shadow-lg hover:shadow-indigo-500/25"
          @click="$emit('next')"
        >
          Continuer →
        </button>
      </div>
  
    </div>
  </template>
  
  <script setup>
  defineProps({
    question: Object,
    selected: Number,
    isCorrect: Boolean
  })
  
  defineEmits(['next'])
  </script>
  
  <style scoped>
  .animate-fade-up {
    animation: fadeUp 0.3s ease;
  }
  
  @keyframes fadeUp {
    from { opacity: 0; transform: translateY(16px); }
    to   { opacity: 1; transform: translateY(0); }
  }
  </style>