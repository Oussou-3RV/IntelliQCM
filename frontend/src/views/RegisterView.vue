<template>
  <div class="min-h-screen bg-gray-900 text-white flex items-center justify-center px-4">
    <div class="w-full max-w-md space-y-8">

      <div class="text-center space-y-2">
        <RouterLink to="/" class="inline-flex items-center gap-2 mb-4">
          <span class="text-indigo-400 text-2xl">◈</span>
          <span class="text-white font-black text-xl tracking-tight">QuizAI</span>
        </RouterLink>
        <h1 class="text-2xl font-black tracking-tight">Créer un compte</h1>
        <p class="text-gray-400 text-sm">Gratuit — aucune carte de crédit requise</p>
      </div>

      <form @submit.prevent="handleSubmit" class="space-y-4">
        <div class="space-y-1">
          <label class="text-sm text-gray-400">Nom</label>
          <input
            v-model="form.name"
            type="text"
            placeholder="Ton prénom"
            class="w-full bg-gray-800 border border-gray-700 rounded-lg px-4 py-3 text-white placeholder-gray-500 focus:outline-none focus:border-indigo-500 transition-colors"
            required
          />
        </div>

        <div class="space-y-1">
          <label class="text-sm text-gray-400">Email</label>
          <input
            v-model="form.email"
            type="email"
            placeholder="toi@exemple.com"
            class="w-full bg-gray-800 border border-gray-700 rounded-lg px-4 py-3 text-white placeholder-gray-500 focus:outline-none focus:border-indigo-500 transition-colors"
            required
          />
        </div>

        <div class="space-y-1">
          <label class="text-sm text-gray-400">Mot de passe</label>
          <input
            v-model="form.password"
            type="password"
            placeholder="6 caractères minimum"
            class="w-full bg-gray-800 border border-gray-700 rounded-lg px-4 py-3 text-white placeholder-gray-500 focus:outline-none focus:border-indigo-500 transition-colors"
            required
          />
        </div>

        <p v-if="error" class="text-red-400 text-sm">{{ error }}</p>

        <button
          type="submit"
          :disabled="loading"
          class="w-full bg-indigo-600 hover:bg-indigo-500 disabled:opacity-50 disabled:cursor-not-allowed text-white font-semibold py-3 rounded-lg transition-colors"
        >
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
