<template>
  <section id="tarifs" class="px-6 py-20 border-t border-gray-800">
    <div class="max-w-4xl mx-auto space-y-12">

      <div ref="titleEl" class="text-center space-y-3 reveal-block" :class="{ visible: titleVisible }">
        <h2 class="text-3xl font-black text-white tracking-tight">Tarifs simples</h2>
        <p class="text-gray-400">Commence gratuitement, passe en Premium quand tu en as besoin.</p>
      </div>

      <div ref="cardsEl" class="grid md:grid-cols-2 gap-6 reveal-block" :class="{ visible: cardsVisible }">

        <!-- Free -->
        <div class="bg-gray-800/50 border border-gray-700/50 rounded-xl p-8 space-y-6">
          <div>
            <p class="text-gray-400 text-sm font-medium mb-1">Gratuit</p>
            <p class="text-4xl font-black text-white">0 $</p>
            <p class="text-gray-500 text-sm mt-1">Pour toujours</p>
          </div>
          <ul class="space-y-3 text-sm text-gray-400">
            <li v-for="item in freeFeatures" :key="item" class="flex items-center gap-2.5">
              <Minus :size="14" class="text-gray-600 shrink-0" />
              {{ item }}
            </li>
          </ul>
          <RouterLink
            to="/register"
            class="flex items-center justify-center gap-2 text-sm font-semibold text-white border border-gray-600 hover:border-gray-500 px-6 py-3 rounded-lg transition-colors"
          >
            Commencer gratuitement
            <ArrowRight :size="14" />
          </RouterLink>
        </div>

        <!-- Premium -->
        <div class="bg-indigo-950/60 border border-indigo-500/40 rounded-xl p-8 space-y-6 relative">
          <div class="absolute top-4 right-4 flex items-center gap-1 text-xs font-semibold bg-indigo-500 text-white px-2.5 py-1 rounded-full">
            <Star :size="11" />
            Recommandé
          </div>
          <div>
            <p class="text-indigo-300 text-sm font-medium mb-1">Premium</p>
            <p class="text-4xl font-black text-white">9 $<span class="text-lg text-gray-400 font-normal"> /mois</span></p>
            <p class="text-gray-500 text-sm mt-1">Annulable à tout moment</p>
          </div>
          <ul class="space-y-3 text-sm text-gray-300">
            <li v-for="item in premiumFeatures" :key="item" class="flex items-center gap-2.5">
              <CheckCircle :size="14" class="text-indigo-400 shrink-0" />
              {{ item }}
            </li>
          </ul>
          <RouterLink
            to="/register?plan=premium"
            class="flex items-center justify-center gap-2 text-sm font-semibold bg-indigo-600 hover:bg-indigo-500 text-white px-6 py-3 rounded-lg transition-all hover:shadow-lg hover:shadow-indigo-500/25"
          >
            <Zap :size="14" />
            Essayer Premium
          </RouterLink>
        </div>

      </div>
    </div>
  </section>
</template>

<script setup>
import { RouterLink } from 'vue-router'
import { CheckCircle, Minus, ArrowRight, Star, Zap } from 'lucide-vue-next'
import { useScrollReveal } from '@/composables/useScrollReveal'

const { el: titleEl, isVisible: titleVisible } = useScrollReveal()
const { el: cardsEl, isVisible: cardsVisible } = useScrollReveal()

const freeFeatures = [
  '3 quiz par jour',
  'Maximum 10 questions par quiz',
  'Feedback immédiat',
  'Rapport de fin de session'
]

const premiumFeatures = [
  'Quiz illimités',
  "Jusqu'à 20 questions par quiz",
  'Mode examen',
  'Historique complet des sessions',
  'Tableau de bord et statistiques',
  'Bibliothèque de cours sauvegardés'
]
</script>

<style scoped>
.reveal-block {
  opacity: 0;
  transform: translateY(28px);
  transition: opacity 0.55s ease, transform 0.55s ease;
}
.reveal-block.visible {
  opacity: 1;
  transform: translateY(0);
}
</style>
