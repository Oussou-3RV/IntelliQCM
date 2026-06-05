<template>
  <section class="px-6 py-20 border-t border-gray-800">
    <div class="max-w-2xl mx-auto text-center space-y-6">
      <h2 class="text-3xl font-black text-white tracking-tight">
        Prêt à réviser plus intelligemment ?
      </h2>
      <p class="text-gray-400">
        Rejoins les étudiants qui utilisent l'IA pour préparer leurs examens.
        Aucune carte de crédit requise pour commencer.
      </p>
      <RouterLink
        to="/register"
        class="inline-block bg-indigo-600 hover:bg-indigo-500 text-white font-semibold px-10 py-3 rounded-lg transition-colors"
      >
        Créer un compte gratuit
      </RouterLink>
    </div>
  </section>
</template>

<script setup>
import { RouterLink } from 'vue-router'
</script>
