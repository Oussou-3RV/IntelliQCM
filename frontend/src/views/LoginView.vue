<template>
  <div class="min-h-screen bg-gray-950 flex items-center justify-center px-4 relative overflow-hidden">

    <!-- Glow background -->
    <div class="absolute inset-0 pointer-events-none">
      <div class="absolute top-[-100px] left-1/2 -translate-x-1/2 w-[600px] h-[500px] bg-indigo-600/10 rounded-full blur-3xl" />
      <div class="absolute bottom-[-50px] right-1/4 w-[300px] h-[300px] bg-indigo-800/8 rounded-full blur-2xl" />
    </div>

    <!-- Carte -->
    <div class="w-full max-w-md bg-gray-900/80 backdrop-blur border border-gray-800 rounded-2xl p-8 space-y-7 shadow-2xl shadow-black/50 animate-card-up">

      <!-- Logo -->
      <div class="text-center space-y-1">
        <RouterLink to="/" class="inline-flex items-center gap-2 justify-center">
          <GraduationCap :size="42" class="text-indigo-400" />
          <!-- <span class="text-white font-black text-2xl tracking-tight">IntelliQCM</span> -->
        </RouterLink>
        <p class="text-gray-400 text-sm pt-1">Bon retour parmi nous</p>
      </div>

      <!-- Formulaire -->
      <form @submit.prevent="handleSubmit" class="space-y-4">
        <div class="space-y-1.5">
          <label class="text-xs font-semibold text-gray-400 uppercase tracking-widest">Email</label>
          <div class="relative">
            <Mail :size="15" class="absolute left-3.5 top-1/2 -translate-y-1/2 text-gray-500" />
            <input
              v-model="form.email"
              type="email"
              placeholder="Oussou@exemple.com"
              class="w-full bg-gray-800/60 border border-gray-700 hover:border-gray-600 focus:border-indigo-500 rounded-xl pl-10 pr-4 py-3 text-white placeholder-gray-600 text-sm focus:outline-none transition-colors"
              required
            />
          </div>
        </div>

        <div class="space-y-1.5">
          <label class="text-xs font-semibold text-gray-400 uppercase tracking-widest">Mot de passe</label>
          <div class="relative">
            <Lock :size="15" class="absolute left-3.5 top-1/2 -translate-y-1/2 text-gray-500" />
            <input
              v-model="form.password"
              :type="showPassword ? 'text' : 'password'"
              placeholder="••••••••"
              class="w-full bg-gray-800/60 border border-gray-700 hover:border-gray-600 focus:border-indigo-500 rounded-xl pl-10 pr-10 py-3 text-white placeholder-gray-600 text-sm focus:outline-none transition-colors"
              required
            />
            <button
              type="button"
              @click="showPassword = !showPassword"
              class="absolute right-3.5 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-300 transition-colors"
            >
              <Eye v-if="!showPassword" :size="15" />
              <EyeOff v-else :size="15" />
            </button>
          </div>
        </div>

        <!-- Erreur standard -->
        <div v-if="error" class="flex items-center gap-2 bg-red-500/10 border border-red-500/30 rounded-lg px-3 py-2.5 text-red-400 text-sm">
          <AlertCircle :size="14" class="shrink-0" /> {{ error }}
        </div>

        <!-- Email non vérifié -->
        <div v-if="unverified" class="bg-yellow-500/10 border border-yellow-500/30 rounded-lg px-3 py-2.5 space-y-0.5">
          <p class="flex items-center gap-2 text-yellow-400 text-sm font-medium">
            <AlertCircle :size="14" /> Email non vérifié
          </p>
          <p class="text-yellow-400/60 text-xs pl-5">Vérifie ta boîte email et clique sur le lien.</p>
        </div>

        <button
          type="submit"
          :disabled="loading"
          class="w-full flex items-center justify-center gap-2 bg-indigo-600 hover:bg-indigo-500 disabled:opacity-50 disabled:cursor-not-allowed text-white font-semibold py-3 rounded-xl transition-all hover:shadow-lg hover:shadow-indigo-500/20 text-sm"
        >
          <Loader2 v-if="loading" :size="16" class="animate-spin" />
          <LogIn v-else :size="16" />
          {{ loading ? 'Connexion...' : 'Se connecter' }}
        </button>
      </form>

      <!-- Séparateur -->
      <div class="flex items-center gap-3">
        <div class="flex-1 h-px bg-gray-800" />
        <span class="text-xs text-gray-600">ou</span>
        <div class="flex-1 h-px bg-gray-800" />
      </div>

      <!-- Google -->
      <a
        :href="googleAuthUrl"
        class="flex items-center justify-center gap-3 w-full border border-gray-700 hover:border-gray-600 bg-gray-800/40 hover:bg-gray-800 text-white text-sm font-medium py-3 rounded-xl transition-all"
      >
        <svg class="w-4 h-4 shrink-0" viewBox="0 0 24 24">
          <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
          <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
          <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l3.66-2.84z"/>
          <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
        </svg>
        Continuer avec Google
      </a>

      <p class="text-center text-sm text-gray-500">
        Pas encore de compte ?
        <RouterLink to="/register" class="text-indigo-400 hover:text-indigo-300 transition-colors font-medium">
          S'inscrire gratuitement
        </RouterLink>
      </p>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { Mail, Lock, LogIn, Loader2, AlertCircle, Eye, EyeOff, GraduationCap } from 'lucide-vue-next'

const googleAuthUrl = `${import.meta.env.VITE_API_URL}/oauth2/authorization/google`

const router = useRouter()
const auth = useAuthStore()

const form = ref({ email: '', password: '' })
const loading = ref(false)
const error = ref('')
const unverified = ref(false)
const showPassword = ref(false)

async function handleSubmit() {
  error.value = ''
  unverified.value = false
  loading.value = true
  try {
    await auth.login(form.value.email, form.value.password)
    router.push('/app')
  } catch (e) {
    if (e.message.startsWith('unverified:')) {
      unverified.value = true
    } else {
      error.value = e.message
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
@keyframes cardUp {
  from { opacity: 0; transform: translateY(20px); }
  to   { opacity: 1; transform: translateY(0); }
}
.animate-card-up {
  animation: cardUp 0.5s ease forwards;
}
</style>
