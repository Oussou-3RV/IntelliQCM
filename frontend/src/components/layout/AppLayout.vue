<template>
  <div class="min-h-screen bg-gray-900 text-white">

    <!-- Header -->
    <header class="border-b border-gray-800 px-4 sm:px-6 py-4 sticky top-0 z-50 bg-gray-900/95 backdrop-blur">
      <div class="max-w-4xl mx-auto flex items-center justify-between">

        <!-- Logo -->
        <RouterLink to="/app" class="flex items-center gap-2">
          <span class="text-indigo-400 text-2xl">◈</span>
          <span class="text-white font-black text-xl tracking-tight">QuizAI</span>
        </RouterLink>

        <!-- Nav desktop -->
        <nav class="hidden md:flex items-center gap-6 text-sm text-gray-400">
          <RouterLink
            v-for="link in navLinks"
            :key="link.to"
            :to="link.to"
            class="hover:text-white transition-colors"
            :class="{ 'text-white font-medium': isActive(link.to) }"
          >
            {{ link.label }}
          </RouterLink>
        </nav>

        <!-- Droite : user + déconnexion (desktop) + hamburger (mobile) -->
        <div class="flex items-center gap-3">
          <!-- User + logout desktop -->
          <div class="hidden md:flex items-center gap-3 text-sm">
            <span class="text-gray-500">{{ auth.user?.name }}</span>
            <button
              @click="handleLogout"
              class="text-gray-400 hover:text-white transition-colors border border-gray-700 hover:border-gray-600 px-3 py-1.5 rounded-lg"
            >
              Déconnexion
            </button>
          </div>

          <!-- Hamburger mobile -->
          <button
            class="md:hidden flex flex-col gap-1.5 p-2"
            @click="menuOpen = !menuOpen"
            aria-label="Menu"
          >
            <span
              class="block w-5 h-0.5 bg-gray-400 transition-all duration-200"
              :class="menuOpen ? 'rotate-45 translate-y-2' : ''"
            />
            <span
              class="block w-5 h-0.5 bg-gray-400 transition-all duration-200"
              :class="menuOpen ? 'opacity-0' : ''"
            />
            <span
              class="block w-5 h-0.5 bg-gray-400 transition-all duration-200"
              :class="menuOpen ? '-rotate-45 -translate-y-2' : ''"
            />
          </button>
        </div>

      </div>

      <!-- Menu mobile déroulant -->
      <div
        v-if="menuOpen"
        class="md:hidden border-t border-gray-800 mt-4 pt-4 pb-2 space-y-1"
        @click="menuOpen = false"
      >
        <RouterLink
          v-for="link in navLinks"
          :key="link.to"
          :to="link.to"
          class="flex items-center gap-3 px-2 py-2.5 rounded-lg text-sm transition-colors"
          :class="isActive(link.to)
            ? 'bg-indigo-600/20 text-white font-medium'
            : 'text-gray-400 hover:text-white hover:bg-gray-800'"
        >
          <span class="text-indigo-400 text-xs">{{ link.icon }}</span>
          {{ link.label }}
        </RouterLink>

        <div class="border-t border-gray-800 mt-3 pt-3 px-2 flex items-center justify-between">
          <span class="text-gray-500 text-sm">{{ auth.user?.name }}</span>
          <button
            @click="handleLogout"
            class="text-sm text-gray-400 hover:text-white transition-colors"
          >
            Déconnexion
          </button>
        </div>
      </div>
    </header>

    <!-- Contenu -->
    <main>
      <slot />
    </main>

  </div>
</template>

<script setup>
import { ref } from 'vue'
import { RouterLink, useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()
const router = useRouter()
const route = useRoute()
const menuOpen = ref(false)

const navLinks = [
  { to: '/app',       label: 'Nouveau quiz', icon: '◈' },
  { to: '/history',   label: 'Historique',   icon: '◆' },
  { to: '/dashboard', label: 'Dashboard',    icon: '◉' },
]

function isActive(path) {
  return route.path === path
}

function handleLogout() {
  auth.logout()
  router.push('/')
}
</script>
