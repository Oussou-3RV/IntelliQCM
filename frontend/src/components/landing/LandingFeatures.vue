<template>
  <section id="fonctionnalites" class="px-6 py-20 border-t border-gray-800">
    <div class="max-w-5xl mx-auto space-y-14">
      <div class="text-center space-y-3">
        <h2 class="text-3xl font-black text-white tracking-tight">Comment ça fonctionne</h2>
        <p class="text-gray-400 max-w-xl mx-auto">
          Trois étapes, zéro friction. L'IA fait le travail, tu révises.
        </p>
      </div>

      <div class="grid md:grid-cols-3 gap-6">
        <div
          v-for="feature in features"
          :key="feature.step"
          class="bg-gray-800/50 border border-gray-700/50 rounded-xl p-6 space-y-3"
        >
          <div class="text-indigo-400 font-black text-sm">0{{ feature.step }}</div>
          <h3 class="text-white font-bold text-lg">{{ feature.title }}</h3>
          <p class="text-gray-400 text-sm leading-relaxed">{{ feature.description }}</p>
        </div>
      </div>

      <div class="grid md:grid-cols-2 gap-6 pt-4">
        <div
          v-for="extra in extras"
          :key="extra.title"
          class="flex gap-4 bg-gray-800/30 border border-gray-700/40 rounded-xl p-5"
        >
          <div class="text-indigo-400 font-black text-lg mt-0.5">{{ extra.icon }}</div>
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
const features = [
  {
    step: 1,
    title: 'Upload ton cours',
    description: 'Dépose un PDF ou colle directement ton texte. QuizAI extrait et analyse le contenu automatiquement.'
  },
  {
    step: 2,
    title: 'Configure ton quiz',
    description: 'Choisis le nombre de questions, la difficulté et le mode (révision ou examen). Le reste est géré par l\'IA.'
  },
  {
    step: 3,
    title: 'Révise et progresse',
    description: 'Réponds aux questions, reçois un feedback immédiat et un rapport personnalisé avec tes points faibles.'
  }
]

const extras = [
  {
    icon: '◈',
    title: 'Feedback instantané',
    description: 'Après chaque réponse, l\'IA explique pourquoi tu as bon ou faux, avec les notions clés à retenir.'
  },
  {
    icon: '◇',
    title: 'Rapport personnalisé',
    description: 'En fin de quiz, un bilan complet identifie tes lacunes et te propose un plan de révision ciblé.'
  },
  {
    icon: '◆',
    title: 'Niveaux de difficulté',
    description: 'Facile, moyen ou difficile — adapte l\'intensité des questions à ton niveau et à tes objectifs.'
  },
  {
    icon: '◉',
    title: 'Mode examen',
    description: 'Pas de feedback pendant le quiz, résultats uniquement à la fin. Simule les conditions réelles d\'examen.'
  }
]
</script>
