<template>
  <header class="border-b border-gray-800 px-4 sm:px-6 py-4 sticky top-0 z-50 bg-gray-950/95 backdrop-blur">
    <div class="max-w-5xl mx-auto flex items-center justify-between">

      <!-- Logo -->
      <div class="flex items-center gap-2">
        <!-- <span class="text-indigo-400 text-2xl">◈</span> -->
        <GraduationCap :size="36" class="text-indigo-400" />
        <span class="text-white font-black text-xl tracking-tight">IntelliQCM</span>
      </div>

      <!-- Nav desktop -->
      <nav class="hidden md:flex items-center gap-8 text-sm text-gray-400">
        <a href="#fonctionnalites" class="hover:text-white transition-colors">Fonctionnalités</a>
        <a href="#tarifs" class="hover:text-white transition-colors">Tarifs</a>
      </nav>

      <!-- Actions desktop -->
      <div class="hidden md:flex items-center gap-3">
        <template v-if="auth.isAuthenticated">
          <RouterLink to="/app" class="text-sm text-gray-400 hover:text-white transition-colors">
            Mon quiz
          </RouterLink>
          <button @click="handleLogout" class="text-sm text-gray-400 hover:text-white transition-colors">
            Déconnexion
          </button>
        </template>
        <template v-else>
          <RouterLink to="/login" class="text-sm text-gray-400 hover:text-white transition-colors">
            Connexion
          </RouterLink>
          <RouterLink
            to="/register"
            class="text-sm bg-indigo-600 hover:bg-indigo-500 text-white px-4 py-2 rounded-lg transition-colors font-medium"
          >
            Commencer
          </RouterLink>
        </template>
      </div>

      <!-- Hamburger mobile -->
      <button class="md:hidden flex flex-col gap-1.5 p-2" @click="menuOpen = !menuOpen" aria-label="Menu">
        <span class="block w-5 h-0.5 bg-gray-400 transition-all duration-200" :class="menuOpen ? 'rotate-45 translate-y-2' : ''" />
        <span class="block w-5 h-0.5 bg-gray-400 transition-all duration-200" :class="menuOpen ? 'opacity-0' : ''" />
        <span class="block w-5 h-0.5 bg-gray-400 transition-all duration-200" :class="menuOpen ? '-rotate-45 -translate-y-2' : ''" />
      </button>

    </div>

    <!-- Menu mobile -->
    <div v-if="menuOpen" class="md:hidden border-t border-gray-800 mt-4 pt-4 pb-2 space-y-1" @click="menuOpen = false">
      <a href="#fonctionnalites" class="block px-2 py-2.5 text-sm text-gray-400 hover:text-white rounded-lg hover:bg-gray-800 transition-colors">Fonctionnalités</a>
      <a href="#tarifs" class="block px-2 py-2.5 text-sm text-gray-400 hover:text-white rounded-lg hover:bg-gray-800 transition-colors">Tarifs</a>
      <div class="border-t border-gray-800 mt-3 pt-3 space-y-1">
        <template v-if="auth.isAuthenticated">
          <RouterLink to="/app" class="block px-2 py-2.5 text-sm text-gray-400 hover:text-white rounded-lg hover:bg-gray-800 transition-colors">Mon quiz</RouterLink>
          <button @click="handleLogout" class="block w-full text-left px-2 py-2.5 text-sm text-gray-400 hover:text-white rounded-lg hover:bg-gray-800 transition-colors">Déconnexion</button>
        </template>
        <template v-else>
          <RouterLink to="/login" class="block px-2 py-2.5 text-sm text-gray-400 hover:text-white rounded-lg hover:bg-gray-800 transition-colors">Connexion</RouterLink>
          <RouterLink to="/register" class="block px-2 py-2.5 text-sm text-white font-medium bg-indigo-600 hover:bg-indigo-500 rounded-lg transition-colors text-center mt-2">Commencer</RouterLink>
        </template>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { GraduationCap } from 'lucide-vue-next'

const auth = useAuthStore()
const router = useRouter()
const menuOpen = ref(false)

function handleLogout() {
  auth.logout()
  router.push('/')
}
</script>
