import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

const API = 'http://localhost:8080/api/auth'

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

    _persist(data)
  }

  async function login(email, password) {
    const res = await fetch(`${API}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    })

    const data = await res.json()
    if (!res.ok) throw new Error(data.message || 'Email ou mot de passe incorrect')

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

  return { token, user, isAuthenticated, isPremium, register, login, logout }
})
