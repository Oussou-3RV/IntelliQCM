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

        <!-- Nav desktop uniquement -->
        <nav class="hidden md:flex items-center gap-1">
          <RouterLink
            v-for="link in navLinks"
            :key="link.to"
            :to="link.to"
            class="flex items-center gap-2 px-3 py-2 rounded-lg text-sm transition-all duration-150"
            :class="isActive(link.to)
              ? 'bg-indigo-600/20 text-indigo-400 font-medium'
              : 'text-gray-400 hover:text-white hover:bg-gray-800'"
          >
            <component :is="link.icon" :size="15" :stroke-width="isActive(link.to) ? 2.5 : 1.8" />
            {{ link.label }}
          </RouterLink>
        </nav>

        <!-- Droite : user + déconnexion desktop / nom + déco mobile -->
        <div class="flex items-center gap-3">
          <!-- Desktop -->
          <div class="hidden md:flex items-center gap-3 text-sm">
            <span class="text-gray-500">{{ auth.user?.name }}</span>
            <button
              @click="handleLogout"
              class="flex items-center gap-1.5 text-gray-400 hover:text-white transition-colors border border-gray-700 hover:border-gray-600 px-3 py-1.5 rounded-lg"
            >
              <LogOut :size="14" />
              Déconnexion
            </button>
          </div>

          <!-- Mobile : juste le nom + déconnexion compact -->
          <div class="md:hidden flex items-center gap-3 text-sm">
            <span class="text-gray-500 text-xs">{{ auth.user?.name }}</span>
            <button
              @click="handleLogout"
              class="text-xs text-gray-400 hover:text-white transition-colors"
            >
              Déco.
            </button>
          </div>
        </div>

      </div>
    </header>

    <!-- Contenu — padding bas sur mobile pour laisser place à la bottom nav -->
    <main class="pb-24 md:pb-0">
      <slot />
    </main>

    <!-- Bottom nav mobile -->
    <BottomNav />

  </div>
</template>

<script setup>
import { RouterLink, useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { BookOpen, History, LayoutDashboard, LogOut } from 'lucide-vue-next'
import BottomNav from './BottomNav.vue'

const auth = useAuthStore()
const router = useRouter()
const route = useRoute()

const navLinks = [
  { to: '/app',       label: 'Nouveau quiz', icon: BookOpen        },
  { to: '/history',   label: 'Historique',   icon: History         },
  { to: '/dashboard', label: 'Dashboard',    icon: LayoutDashboard },
]

function isActive(path) {
  return route.path === path
}

function handleLogout() {
  auth.logout()
  router.push('/')
}
</script>
