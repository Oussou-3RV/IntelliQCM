<template>
  <div class="min-h-screen bg-gray-900 text-white flex items-center justify-center px-4">
    <div class="w-full max-w-md text-center space-y-6">

      <!-- Chargement -->
      <template v-if="status === 'loading'">
        <Loader2 :size="36" class="animate-spin text-indigo-400 mx-auto" />
        <p class="text-gray-400">Vérification en cours...</p>
      </template>

      <!-- Succès -->
      <template v-else-if="status === 'success'">
        <div class="flex justify-center">
          <div class="w-16 h-16 rounded-2xl bg-green-600/20 border border-green-500/30 flex items-center justify-center">
            <CheckCircle :size="30" class="text-green-400" />
          </div>
        </div>
        <div class="space-y-2">
          <h1 class="text-2xl font-black tracking-tight">Email confirmé !</h1>
          <p class="text-gray-400 text-sm">Ton compte est activé. Tu peux maintenant te connecter.</p>
        </div>
        <RouterLink
          to="/login"
          class="inline-flex items-center gap-2 bg-indigo-600 hover:bg-indigo-500 text-white font-semibold px-8 py-3 rounded-lg transition-colors"
        >
          <LogIn :size="16" /> Se connecter
        </RouterLink>
      </template>

      <!-- Erreur -->
      <template v-else>
        <div class="flex justify-center">
          <div class="w-16 h-16 rounded-2xl bg-red-600/20 border border-red-500/30 flex items-center justify-center">
            <XCircle :size="30" class="text-red-400" />
          </div>
        </div>
        <div class="space-y-2">
          <h1 class="text-2xl font-black tracking-tight">Lien invalide</h1>
          <p class="text-gray-400 text-sm">Ce lien est invalide ou a déjà été utilisé.</p>
        </div>
        <RouterLink to="/register" class="text-indigo-400 hover:text-indigo-300 text-sm transition-colors">
          Créer un nouveau compte
        </RouterLink>
      </template>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { Loader2, CheckCircle, XCircle, LogIn } from 'lucide-vue-next'

const route = useRoute()
const status = ref('loading')

onMounted(async () => {
  const token = route.query.token
  if (!token) { status.value = 'error'; return }

  try {
    const res = await fetch(`${import.meta.env.VITE_API_URL}/api/auth/verify?token=${token}`)
    status.value = res.ok ? 'success' : 'error'
  } catch {
    status.value = 'error'
  }
})
</script>
