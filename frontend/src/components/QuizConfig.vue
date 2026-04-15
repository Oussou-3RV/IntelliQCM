<template>
    <div class="bg-gray-800/50 border border-gray-700 rounded-2xl p-6 space-y-6">
      <h2 class="text-white font-bold text-xl">Configurer ton quiz</h2>
  
      <!-- Nombre de questions -->
      <div class="space-y-3">
        <label class="text-gray-400 text-xs uppercase tracking-widest font-semibold">
          Nombre de questions
        </label>
        <div class="flex gap-3 flex-wrap">
          <button
            v-for="n in [5, 10, 15, 20]"
            :key="n"
            class="px-5 py-2 rounded-full border text-sm font-medium transition-all duration-200"
            :class="modelValue.questionCount === n
              ? 'bg-indigo-600 border-indigo-600 text-white'
              : 'bg-gray-700 border-gray-600 text-gray-300 hover:border-indigo-400'"
            @click="update('questionCount', n)"
          >
            {{ n }}
          </button>
        </div>
      </div>
  
      <!-- Difficulté -->
      <div class="space-y-3">
        <label class="text-gray-400 text-xs uppercase tracking-widest font-semibold">
          Niveau de difficulté
        </label>
        <div class="flex gap-3 flex-wrap">
          <button
            v-for="d in difficulties"
            :key="d.value"
            class="px-5 py-2 rounded-full border text-sm font-medium transition-all duration-200"
            :class="modelValue.difficulty === d.value
              ? 'bg-indigo-600 border-indigo-600 text-white'
              : 'bg-gray-700 border-gray-600 text-gray-300 hover:border-indigo-400'"
            @click="update('difficulty', d.value)"
          >
            {{ d.emoji }} {{ d.label }}
          </button>
        </div>
      </div>
  
      <!-- Bouton générer -->
      <button
        class="w-full py-3 rounded-full font-semibold text-white transition-all duration-200 flex items-center justify-center gap-2"
        :class="loading
          ? 'bg-indigo-400 cursor-not-allowed'
          : 'bg-indigo-600 hover:bg-indigo-500 hover:shadow-lg hover:shadow-indigo-500/25'"
        :disabled="loading"
        @click="$emit('start')"
      >
        <svg
          v-if="loading"
          class="animate-spin h-5 w-5 text-white"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
        >
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z"/>
        </svg>
        <span>{{ loading ? 'Génération en cours...' : 'Générer le quiz ✦' }}</span>
      </button>
    </div>
  </template>
  
  <script setup>
  const props = defineProps({
    modelValue: Object,
    loading: Boolean
  })
  
  const emit = defineEmits(['update:modelValue', 'start'])
  
  const difficulties = [
    { value: 'facile',    label: 'Facile',    emoji: '🟢' },
    { value: 'moyen',     label: 'Moyen',     emoji: '🟡' },
    { value: 'difficile', label: 'Difficile', emoji: '🔴' }
  ]
  
  function update(key, val) {
    emit('update:modelValue', { ...props.modelValue, [key]: val })
  }
  </script>