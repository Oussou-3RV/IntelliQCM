<template>
    <div class="space-y-6 animate-fade-up">
  
      <!-- Énoncé -->
      <div class="bg-gray-800/50 border border-gray-700 rounded-2xl p-6">
        <p class="text-indigo-400 text-xs uppercase tracking-widest font-semibold mb-3">
          Question
        </p>
        <h2 class="text-white text-xl font-semibold leading-relaxed">
          {{ question.question }}
        </h2>
      </div>
  
      <!-- Choix -->
      <div class="space-y-3">
        <button
          v-for="(choice, i) in question.choices"
          :key="i"
          class="w-full flex items-center gap-4 bg-gray-800/50 border border-gray-700 rounded-2xl p-4 text-left transition-all duration-200 hover:border-indigo-400 hover:bg-indigo-500/5 hover:translate-x-1"
          @click="$emit('answered', i)"
        >
          <span
            class="w-9 h-9 rounded-xl bg-gray-700 border border-gray-600 flex items-center justify-center font-bold text-sm text-gray-300 flex-shrink-0"
          >
            {{ letters[i] }}
          </span>
          <span class="text-gray-200 leading-snug">
            {{ choice.replace(/^[A-D]\.\s*/, '') }}
          </span>
        </button>
      </div>
  
    </div>
  </template>
  
  <script setup>
  defineProps({ question: Object })
  defineEmits(['answered'])
  
  const letters = ['A', 'B', 'C', 'D']
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