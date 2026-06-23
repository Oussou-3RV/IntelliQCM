import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

const API = `${import.meta.env.VITE_API_URL}/api/auth`

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || null)
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))

  const isAuthenticated = computed(() => !!token.value)
  const isPremium = computed(() => user.value?.role === 'PREMIUM')

  async function register(name, email, password) {
    const res = await fetch(`${API}/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email, password })
    })
    const data = await res.json()
    if (!res.ok) throw new Error(data.message || 'Erreur lors de l\'inscription')
    // Pas de _persist ici — l'user doit vérifier son email avant de se connecter
  }

  async function login(email, password) {
    const res = await fetch(`${API}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    })
    const data = await res.json()
    if (!res.ok) {
      if (data.unverified) throw new Error('unverified:' + data.message)
      throw new Error(data.message || 'Email ou mot de passe incorrect')
    }
    _persist(data)
  }

  function logout() {
    token.value = null
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  function _persist(data) {
    token.value = data.token
    user.value = { name: data.name, email: data.email, role: data.role }
    localStorage.setItem('token', data.token)
    localStorage.setItem('user', JSON.stringify(user.value))
  }

  // Appelé après le callback OAuth2 Google
  function _persistFromOAuth(jwtToken, userData) {
    token.value = jwtToken
    user.value = { name: userData.name, email: userData.email, role: userData.role }
    localStorage.setItem('token', jwtToken)
    localStorage.setItem('user', JSON.stringify(user.value))
  }

  return { token, user, isAuthenticated, isPremium, register, login, logout, _persistFromOAuth }
})
