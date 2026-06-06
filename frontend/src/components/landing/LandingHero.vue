<template>
  <section class="px-6 py-24 text-center">
    <div class="max-w-3xl mx-auto space-y-8">
      <div class="space-y-4">
        <span class="text-xs font-semibold uppercase tracking-widest text-indigo-400 border border-indigo-400/30 px-3 py-1 rounded-full">
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

      <div class="flex flex-col sm:flex-row items-center justify-center gap-4">
        <RouterLink
          to="/register"
          class="w-full sm:w-auto bg-indigo-600 hover:bg-indigo-500 text-white font-semibold px-8 py-3 rounded-lg transition-colors"
        >
          Essayer gratuitement
        </RouterLink>
        <RouterLink
          to="/app"
          class="w-full sm:w-auto text-gray-400 hover:text-white font-medium px-8 py-3 rounded-lg border border-gray-700 hover:border-gray-600 transition-colors"
        >
          Voir la démo
        </RouterLink>
      </div>

      <p class="text-xs text-gray-500">Gratuit — 3 quiz par jour sans inscription</p>
    </div>
  </section>
</template>

<script setup>
import { RouterLink } from 'vue-router'
</script>
