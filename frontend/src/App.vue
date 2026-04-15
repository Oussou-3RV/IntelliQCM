
<template>
  <div class="min-h-screen bg-gray-900">

    <!-- Header -->
    <header class="border-b border-gray-800 px-6 py-4">
      <div class="max-w-2xl mx-auto flex items-center gap-2">
        <span class="text-indigo-400 text-2xl">◈</span>
        <span class="text-white font-black text-xl tracking-tight font-sans">QuizAI</span>
      </div>
    </header>

    <!-- Page -->
    <RouterView />

  </div>
</template>

<script setup>
import { RouterView } from 'vue-router'
</script>

