<template>
  <div class="min-h-screen bg-gray-900 text-white flex items-center justify-center px-4">
    <div class="w-full max-w-md space-y-8">

      <div class="text-center space-y-2">
        <RouterLink to="/" class="inline-flex items-center gap-2 mb-4">
          <GraduationCap :size="36" class="text-indigo-400" />
          <span class="text-white font-black text-xl tracking-tight">IntelliQCM</span>
        </RouterLink>
        <h1 class="text-2xl font-black tracking-tight">Créer un compte</h1>
        <p class="text-gray-400 text-sm">Gratuit — aucune carte de crédit requise</p>
      </div>

      <form @submit.prevent="handleSubmit" class="space-y-4">
        <div class="space-y-1">
          <label class="text-sm text-gray-400">Nom</label>
          <div class="relative">
            <User :size="16" class="absolute left-3 top-1/2 -translate-y-1/2 text-gray-500" />
            <input
              v-model="form.name"
              type="text"
              placeholder="Nom"
              class="w-full bg-gray-800 border border-gray-700 rounded-lg pl-9 pr-4 py-3 text-white placeholder-gray-500 focus:outline-none focus:border-indigo-500 transition-colors"
              required
            />
          </div>
        </div>

        <div class="space-y-1">
          <label class="text-sm text-gray-400">Email</label>
          <div class="relative">
            <Mail :size="16" class="absolute left-3 top-1/2 -translate-y-1/2 text-gray-500" />
            <input
              v-model="form.email"
              type="email"
              placeholder="Oussou@exemple.com"
              class="w-full bg-gray-800 border border-gray-700 rounded-lg pl-9 pr-4 py-3 text-white placeholder-gray-500 focus:outline-none focus:border-indigo-500 transition-colors"
              required
            />
          </div>
        </div>

        <div class="space-y-1">
          <label class="text-sm text-gray-400">Mot de passe</label>
          <div class="relative">
            <Lock :size="16" class="absolute left-3 top-1/2 -translate-y-1/2 text-gray-500" />
            <input
              v-model="form.password"
              type="password"
              placeholder="6 caractères minimum"
              class="w-full bg-gray-800 border border-gray-700 rounded-lg pl-9 pr-4 py-3 text-white placeholder-gray-500 focus:outline-none focus:border-indigo-500 transition-colors"
              required
            />
          </div>
        </div>

        <p v-if="error" class="flex items-center gap-2 text-red-400 text-sm">
          <AlertCircle :size="14" /> {{ error }}
        </p>

        <button
          type="submit"
          :disabled="loading"
          class="w-full flex items-center justify-center gap-2 bg-indigo-600 hover:bg-indigo-500 disabled:opacity-50 disabled:cursor-not-allowed text-white font-semibold py-3 rounded-lg transition-colors"
        >
          <Loader2 v-if="loading" :size="16" class="animate-spin" />
          <UserPlus v-else :size="16" />
          {{ loading ? 'Création...' : 'Créer mon compte' }}
        </button>
      </form>

      <!-- Séparateur -->
      <div class="flex items-center gap-3">
        <div class="flex-1 h-px bg-gray-800" />
        <span class="text-xs text-gray-600">ou</span>
        <div class="flex-1 h-px bg-gray-800" />
      </div>

      <!-- Bouton Google -->
      <a
        href="http://localhost:8080/oauth2/authorization/google"
        class="flex items-center justify-center gap-3 w-full border border-gray-700 hover:border-gray-600 bg-gray-800/50 hover:bg-gray-800 text-white text-sm font-medium py-3 rounded-lg transition-colors"
      >
        <svg class="w-4 h-4" viewBox="0 0 24 24">
          <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
          <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
          <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l3.66-2.84z"/>
          <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
        </svg>
        Continuer avec Google
      </a>

      <p class="text-center text-sm text-gray-400">
        Déjà un compte ?
        <RouterLink to="/login" class="text-indigo-400 hover:text-indigo-300 transition-colors">
          Se connecter
        </RouterLink>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { User, Mail, Lock, UserPlus, Loader2, AlertCircle, GraduationCap } from 'lucide-vue-next'

const router = useRouter()
const auth = useAuthStore()

const form = ref({ name: '', email: '', password: '' })
const loading = ref(false)
const error = ref('')

async function handleSubmit() {
  error.value = ''
  loading.value = true
  try {
    await auth.register(form.value.name, form.value.email, form.value.password)
    router.push('/app')
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}
</script>
