<template>
  <div class="min-h-screen bg-gray-900 flex items-center justify-center">
    <div class="flex flex-col items-center gap-4 text-gray-400">
      <Loader2 :size="32" class="animate-spin text-indigo-400" />
      <p class="text-sm">Connexion en cours...</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Loader2 } from 'lucide-vue-next'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const auth = useAuthStore()

onMounted(async () => {
  const params = new URLSearchParams(window.location.search)
  const token = params.get('token')

  if (token) {
    // Récupère les infos du user depuis le backend avec le token
    try {
      const res = await fetch(`${import.meta.env.VITE_API_URL}/api/auth/me`, {
        headers: { Authorization: `Bearer ${token}` }
      })
      if (res.ok) {
        const user = await res.json()
        auth._persistFromOAuth(token, user)
        router.push('/app')
        return
      }
    } catch {
      // fallback
    }
  }

  // Erreur — retour login
  router.push('/login')
})
</script>
