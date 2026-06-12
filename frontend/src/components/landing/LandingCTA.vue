<template>
  <section class="px-6 py-20 border-t border-gray-800">
    <div
      ref="el"
      class="max-w-2xl mx-auto text-center space-y-6 reveal-block"
      :class="{ visible: isVisible }"
    >
      <div class="flex justify-center">
        <div class="w-14 h-14 rounded-2xl bg-indigo-600/20 border border-indigo-500/30 flex items-center justify-center">
          <GraduationCap :size="26" class="text-indigo-400" />
        </div>
      </div>
      <h2 class="text-3xl font-black text-white tracking-tight">
        Prêt à réviser plus intelligemment ?
      </h2>
      <p class="text-gray-400">
        Rejoins les étudiants qui utilisent l'IA pour préparer leurs examens.
        Aucune carte de crédit requise pour commencer.
      </p>
      <RouterLink
        to="/register"
        class="inline-flex items-center gap-2 bg-indigo-600 hover:bg-indigo-500 text-white font-semibold px-10 py-3 rounded-lg transition-all hover:scale-[1.02] hover:shadow-lg hover:shadow-indigo-500/25"
      >
        Créer un compte gratuit
        <ArrowRight :size="16" />
      </RouterLink>
    </div>
  </section>
</template>

<script setup>
import { RouterLink } from 'vue-router'
import { GraduationCap, ArrowRight } from 'lucide-vue-next'
import { useScrollReveal } from '@/composables/useScrollReveal'

const { el, isVisible } = useScrollReveal()
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
