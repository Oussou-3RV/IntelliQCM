<template>
  <header class="border-b border-gray-800 px-6 py-4 sticky top-0 z-50 bg-gray-900/95 backdrop-blur">
    <div class="max-w-5xl mx-auto flex items-center justify-between">
      <div class="flex items-center gap-2">
        <span class="text-indigo-400 text-2xl">◈</span>
        <span class="text-white font-black text-xl tracking-tight">QuizAI</span>
      </div>
      <nav class="hidden md:flex items-center gap-8 text-sm text-gray-400">
        <a href="#fonctionnalites" class="hover:text-white transition-colors">Fonctionnalités</a>
        <a href="#tarifs" class="hover:text-white transition-colors">Tarifs</a>
      </nav>
      <div class="flex items-center gap-3">
        <RouterLink to="/login" class="text-sm text-gray-400 hover:text-white transition-colors">
          Connexion
        </RouterLink>
        <RouterLink
          to="/register"
          class="text-sm bg-indigo-600 hover:bg-indigo-500 text-white px-4 py-2 rounded-lg transition-colors font-medium"
        >
          Commencer
        </RouterLink>
      </div>
    </div>
  </header>
</template>

<script setup>
import { RouterLink } from 'vue-router'
</script>
