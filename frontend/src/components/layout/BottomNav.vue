<template>
  <nav class="md:hidden fixed bottom-0 left-0 right-0 z-50 px-4 pb-6 pt-2 pointer-events-none">
    <div class="flex items-center justify-around bg-gray-800/90 backdrop-blur-md border border-gray-700/50 rounded-2xl px-2 py-2 shadow-2xl shadow-black/50 pointer-events-auto">
      <RouterLink
        v-for="link in navLinks"
        :key="link.to"
        :to="link.to"
        class="flex flex-col items-center gap-1.5 px-5 py-2 rounded-xl transition-all duration-200"
        :class="isActive(link.to)
          ? 'bg-indigo-600/25 text-indigo-400'
          : 'text-gray-500 active:scale-95'"
      >
        <component
          :is="link.icon"
          :size="22"
          :stroke-width="isActive(link.to) ? 2.5 : 1.8"
        />
        <span class="text-xs font-medium tracking-tight">{{ link.label }}</span>
      </RouterLink>
    </div>
  </nav>
</template>

<script setup>
import { RouterLink, useRoute } from 'vue-router'
import { BookOpen, History, LayoutDashboard } from 'lucide-vue-next'

const route = useRoute()

const navLinks = [
  { to: '/app',       label: 'Quiz',       icon: BookOpen        },
  { to: '/history',   label: 'Historique', icon: History         },
  { to: '/dashboard', label: 'Dashboard',  icon: LayoutDashboard },
]

function isActive(path) {
  return route.path === path
}
</script>
