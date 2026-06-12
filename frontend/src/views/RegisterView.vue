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
