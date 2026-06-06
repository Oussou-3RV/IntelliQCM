const BASE_URL = 'http://localhost:8080/api/dashboard'

function authHeaders() {
  const token = localStorage.getItem('token')
  return {
    'Content-Type': 'application/json',
    ...(token ? { Authorization: `Bearer ${token}` } : {})
  }
}

export async function getDashboard() {
  const res = await fetch(BASE_URL, { headers: authHeaders() })
  if (!res.ok) throw new Error('Erreur lors du chargement du tableau de bord')
  return res.json()
}
