<template>
  <div class="min-h-screen bg-gray-900 text-white flex items-center justify-center px-4">
    <div class="w-full max-w-md text-center space-y-6">

      <div class="flex justify-center">
        <div class="w-16 h-16 rounded-2xl bg-indigo-600/20 border border-indigo-500/30 flex items-center justify-center">
          <Mail :size="30" class="text-indigo-400" />
        </div>
      </div>

      <div class="space-y-2">
        <h1 class="text-2xl font-black tracking-tight">Vérifie ton email</h1>
        <p class="text-gray-400 text-sm leading-relaxed">
          Un email de confirmation a été envoyé à<br />
          <span class="text-white font-medium">{{ email }}</span>
        </p>
      </div>

      <div class="bg-gray-800/50 border border-gray-700/50 rounded-xl p-5 text-left space-y-3 text-sm text-gray-400">
        <p class="flex items-start gap-2"><CheckCircle :size="15" class="text-green-400 mt-0.5 shrink-0" /> Ouvre ta boîte email</p>
        <p class="flex items-start gap-2"><CheckCircle :size="15" class="text-green-400 mt-0.5 shrink-0" /> Clique sur le lien de confirmation</p>
        <p class="flex items-start gap-2"><CheckCircle :size="15" class="text-green-400 mt-0.5 shrink-0" /> Connecte-toi à ton compte</p>
      </div>

      <p class="text-xs text-gray-500">
        Tu n'as pas reçu l'email ? Vérifie tes spams.<br />
        <RouterLink to="/register" class="text-indigo-400 hover:text-indigo-300 transition-colors">
          Réessayer avec un autre email
        </RouterLink>
      </p>

    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { Mail, CheckCircle } from 'lucide-vue-next'

const route = useRoute()
const email = computed(() => route.query.email || 'ton adresse email')
</script>
