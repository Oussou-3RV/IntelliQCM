<template>
  <div class="min-h-screen bg-gray-900 text-white flex items-center justify-center px-4">
    <div class="w-full max-w-lg space-y-8 text-center">

      <div class="space-y-3">
        <RouterLink to="/" class="inline-flex items-center gap-2 justify-center mb-2">
          <span class="text-indigo-400 text-2xl">◈</span>
          <span class="text-white font-black text-xl tracking-tight">QuizAI</span>
        </RouterLink>
        <h1 class="text-3xl font-black tracking-tight">
          Limite quotidienne atteinte
        </h1>
        <p class="text-gray-400 leading-relaxed">
          Tu as utilisé tes 3 quiz gratuits aujourd'hui.<br />
          Reviens demain ou passe en <span class="text-indigo-400 font-semibold">Premium</span> pour un accès illimité.
        </p>
      </div>

      <!-- Comparaison plans -->
      <div class="grid grid-cols-2 gap-4 text-left">
        <div class="bg-gray-800/50 border border-gray-700/50 rounded-xl p-5 space-y-3">
          <p class="text-gray-400 font-semibold text-sm">Gratuit</p>
          <ul class="space-y-2 text-sm text-gray-400">
            <li class="flex items-center gap-2"><span class="text-gray-600">—</span> 3 quiz par jour</li>
            <li class="flex items-center gap-2"><span class="text-gray-600">—</span> Max 10 questions</li>
            <li class="flex items-center gap-2"><span class="text-gray-600">—</span> Historique</li>
          </ul>
        </div>
        <div class="bg-indigo-950/60 border border-indigo-500/40 rounded-xl p-5 space-y-3">
          <p class="text-indigo-300 font-semibold text-sm">Premium — 9 $/mois</p>
          <ul class="space-y-2 text-sm text-gray-300">
            <li class="flex items-center gap-2"><span class="text-indigo-400">◈</span> Quiz illimités</li>
            <li class="flex items-center gap-2"><span class="text-indigo-400">◈</span> Jusqu'à 20 questions</li>
            <li class="flex items-center gap-2"><span class="text-indigo-400">◈</span> Mode examen</li>
            <li class="flex items-center gap-2"><span class="text-indigo-400">◈</span> Tableau de bord</li>
          </ul>
        </div>
      </div>

      <div class="flex flex-col sm:flex-row gap-3 justify-center">
        <button
          class="bg-indigo-600 hover:bg-indigo-500 text-white font-semibold px-8 py-3 rounded-lg transition-colors"
          @click="comingSoon"
        >
          Passer en Premium
        </button>
        <RouterLink
          to="/app"
          class="text-gray-400 hover:text-white border border-gray-700 hover:border-gray-600 font-medium px-8 py-3 rounded-lg transition-colors"
        >
          Retour au quiz
        </RouterLink>
      </div>

      <p class="text-xs text-gray-600">
        Le paiement en ligne sera disponible prochainement.
      </p>

    </div>
  </div>
</template>

<script setup>
import { RouterLink } from 'vue-router'

function comingSoon() {
  alert('Le paiement en ligne sera disponible prochainement.')
}
</script>
