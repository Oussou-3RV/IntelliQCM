<template>
  <section id="tarifs" class="px-6 py-20 border-t border-gray-800">
    <div class="max-w-4xl mx-auto space-y-12">
      <div class="text-center space-y-3">
        <h2 class="text-3xl font-black text-white tracking-tight">Tarifs simples</h2>
        <p class="text-gray-400">Commence gratuitement, passe en Premium quand tu en as besoin.</p>
      </div>

      <div class="grid md:grid-cols-2 gap-6">
        <!-- Free -->
        <div class="bg-gray-800/50 border border-gray-700/50 rounded-xl p-8 space-y-6">
          <div>
            <p class="text-gray-400 text-sm font-medium mb-1">Gratuit</p>
            <p class="text-4xl font-black text-white">0 $</p>
            <p class="text-gray-500 text-sm mt-1">Pour toujours</p>
          </div>
          <ul class="space-y-3 text-sm text-gray-400">
            <li v-for="item in freeFeatures" :key="item" class="flex items-start gap-2">
              <span class="text-gray-500 mt-0.5">—</span>
              {{ item }}
            </li>
          </ul>
          <RouterLink
            to="/register"
            class="block text-center text-sm font-semibold text-white border border-gray-600 hover:border-gray-500 px-6 py-3 rounded-lg transition-colors"
          >
            Commencer gratuitement
          </RouterLink>
        </div>

        <!-- Premium -->
        <div class="bg-indigo-950/60 border border-indigo-500/40 rounded-xl p-8 space-y-6 relative">
          <div class="absolute top-4 right-4 text-xs font-semibold bg-indigo-500 text-white px-2.5 py-1 rounded-full">
            Recommandé
          </div>
          <div>
            <p class="text-indigo-300 text-sm font-medium mb-1">Premium</p>
            <p class="text-4xl font-black text-white">9 $<span class="text-lg text-gray-400 font-normal"> /mois</span></p>
            <p class="text-gray-500 text-sm mt-1">Annulable à tout moment</p>
          </div>
          <ul class="space-y-3 text-sm text-gray-300">
            <li v-for="item in premiumFeatures" :key="item" class="flex items-start gap-2">
              <span class="text-indigo-400 mt-0.5">◈</span>
              {{ item }}
            </li>
          </ul>
          <RouterLink
            to="/register?plan=premium"
            class="block text-center text-sm font-semibold bg-indigo-600 hover:bg-indigo-500 text-white px-6 py-3 rounded-lg transition-colors"
          >
            Essayer Premium
          </RouterLink>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { RouterLink } from 'vue-router'

const freeFeatures = [
  '3 quiz par jour',
  'Maximum 10 questions par quiz',
  'Feedback immédiat',
  'Rapport de fin de session'
]

const premiumFeatures = [
  'Quiz illimités',
  'Jusqu\'à 20 questions par quiz',
  'Mode examen',
  'Historique complet des sessions',
  'Tableau de bord et statistiques',
  'Bibliothèque de cours sauvegardés'
]
</script>
