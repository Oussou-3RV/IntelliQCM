<template>
    <div class="bg-gray-800/50 border border-gray-700 rounded-2xl p-6 space-y-6">
      <h2 class="text-white font-bold text-xl">Configurer ton quiz</h2>

      <!-- Mode -->
      <div class="space-y-3">
        <label class="text-gray-400 text-xs uppercase tracking-widest font-semibold">
          Mode
        </label>
        <div class="grid grid-cols-2 gap-3">
          <button
            v-for="m in modes"
            :key="m.value"
            class="px-4 py-3 rounded-xl border text-sm font-medium transition-all duration-200 text-left space-y-0.5"
            :class="modelValue.mode === m.value
              ? 'bg-indigo-600/20 border-indigo-500 text-white'
              : 'bg-gray-700/50 border-gray-600 text-gray-300 hover:border-indigo-400/50'"
            @click="update('mode', m.value)"
          >
            <p class="font-semibold">{{ m.label }}</p>
            <p class="text-xs opacity-70">{{ m.description }}</p>
          </button>
        </div>
      </div>

      <!-- Timer (mode examen uniquement) -->
      <div v-if="modelValue.mode === 'exam'" class="space-y-3">
        <label class="text-gray-400 text-xs uppercase tracking-widest font-semibold">
          Temps par question
        </label>
        <div class="flex gap-3 flex-wrap">
          <button
            v-for="t in timerOptions"
            :key="t.value"
            class="px-5 py-2 rounded-full border text-sm font-medium transition-all duration-200"
            :class="modelValue.timerSeconds === t.value
              ? 'bg-indigo-600 border-indigo-600 text-white'
              : 'bg-gray-700 border-gray-600 text-gray-300 hover:border-indigo-400'"
            @click="update('timerSeconds', t.value)"
          >
            {{ t.label }}
          </button>
        </div>
      </div>

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

const modes = [
  { value: 'revision', label: 'Révision', description: 'Feedback immédiat après chaque réponse' },
  { value: 'exam',     label: 'Examen',   description: 'Résultats uniquement à la fin' }
]

const timerOptions = [
  { value: 15,  label: '15 sec' },
  { value: 30,  label: '30 sec' },
  { value: 60,  label: '1 min' },
  { value: 120, label: '2 min' }
]

const difficulties = [
  { value: 'facile',    label: 'Facile',    emoji: '🟢' },
  { value: 'moyen',     label: 'Moyen',     emoji: '🟡' },
  { value: 'difficile', label: 'Difficile', emoji: '🔴' }
]

function update(key, val) {
  emit('update:modelValue', { ...props.modelValue, [key]: val })
}
</script>
