<template>
  <section id="fonctionnalites" class="px-6 py-20 border-t border-gray-800">
    <div class="max-w-5xl mx-auto space-y-14">

      <!-- Titre -->
      <div ref="titleEl" class="text-center space-y-3 reveal-block" :class="{ visible: titleVisible }">
        <h2 class="text-3xl font-black text-white tracking-tight">Comment ça fonctionne</h2>
        <p class="text-gray-400 max-w-xl mx-auto">
          Trois étapes, zéro friction. L'IA fait le travail, tu révises.
        </p>
      </div>

      <!-- 3 étapes -->
      <div class="grid md:grid-cols-3 gap-6">
        <div
          v-for="(feature, i) in features"
          :key="feature.step"
          :ref="el => stepsEl[i] = el"
          class="bg-gray-800/50 border border-gray-700/50 rounded-xl p-6 space-y-4 reveal-block"
          :class="{ visible: stepsVisible[i] }"
          :style="{ transitionDelay: `${i * 80}ms` }"
        >
          <div class="flex items-center gap-3">
            <div class="w-9 h-9 rounded-lg bg-indigo-600/20 border border-indigo-500/30 flex items-center justify-center shrink-0">
              <component :is="feature.icon" :size="17" class="text-indigo-400" />
            </div>
            <span class="text-indigo-400 font-black text-xs tracking-widest">ÉTAPE 0{{ feature.step }}</span>
          </div>
          <h3 class="text-white font-bold text-lg">{{ feature.title }}</h3>
          <p class="text-gray-400 text-sm leading-relaxed">{{ feature.description }}</p>
        </div>
      </div>

      <!-- Extras -->
      <div ref="extrasEl" class="grid md:grid-cols-2 gap-6 pt-4 reveal-block" :class="{ visible: extrasVisible }">
        <div
          v-for="(extra, i) in extras"
          :key="extra.title"
          class="flex gap-4 bg-gray-800/30 border border-gray-700/40 rounded-xl p-5 hover:border-gray-600 transition-colors"
          :style="{ transitionDelay: `${i * 60}ms` }"
        >
          <div class="w-10 h-10 rounded-xl bg-indigo-600/15 border border-indigo-500/20 flex items-center justify-center shrink-0 mt-0.5">
            <component :is="extra.icon" :size="18" class="text-indigo-400" />
          </div>
          <div>
            <h4 class="text-white font-semibold mb-1">{{ extra.title }}</h4>
            <p class="text-gray-400 text-sm leading-relaxed">{{ extra.description }}</p>
          </div>
        </div>
      </div>

    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { Upload, Settings2, TrendingUp, Zap, BarChart2, Gauge, Clock } from 'lucide-vue-next'
import { useScrollReveal } from '@/composables/useScrollReveal'

const { el: titleEl, isVisible: titleVisible } = useScrollReveal()
const { el: extrasEl, isVisible: extrasVisible } = useScrollReveal()

// Révéler les cartes d'étapes individuellement
const stepsEl = ref([])
const stepsVisible = ref([false, false, false])

import { onMounted, onUnmounted } from 'vue'

let stepObservers = []

onMounted(() => {
  stepsEl.value.forEach((el, i) => {
    if (!el) return
    const obs = new IntersectionObserver(
      ([entry]) => { if (entry.isIntersecting) { stepsVisible.value[i] = true; obs.disconnect() } },
      { threshold: 0.15 }
    )
    obs.observe(el)
    stepObservers.push(obs)
  })
})

onUnmounted(() => stepObservers.forEach(o => o.disconnect()))

const features = [
  {
    step: 1,
    icon: Upload,
    title: 'Upload ton cours',
    description: 'Dépose un PDF ou colle directement ton texte. QuizAI extrait et analyse le contenu automatiquement.'
  },
  {
    step: 2,
    icon: Settings2,
    title: 'Configure ton quiz',
    description: 'Choisis le nombre de questions, la difficulté et le mode (révision ou examen). Le reste est géré par l\'IA.'
  },
  {
    step: 3,
    icon: TrendingUp,
    title: 'Révise et progresse',
    description: 'Réponds aux questions, reçois un feedback immédiat et un rapport personnalisé avec tes points faibles.'
  }
]

const extras = [
  {
    icon: Zap,
    title: 'Feedback instantané',
    description: 'Après chaque réponse, l\'IA explique pourquoi tu as bon ou faux, avec les notions clés à retenir.'
  },
  {
    icon: BarChart2,
    title: 'Rapport personnalisé',
    description: 'En fin de quiz, un bilan complet identifie tes lacunes et te propose un plan de révision ciblé.'
  },
  {
    icon: Gauge,
    title: 'Niveaux de difficulté',
    description: 'Facile, moyen ou difficile — adapte l\'intensité des questions à ton niveau et à tes objectifs.'
  },
  {
    icon: Clock,
    title: 'Mode examen',
    description: 'Pas de feedback pendant le quiz, résultats uniquement à la fin. Simule les conditions réelles d\'examen.'
  }
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
