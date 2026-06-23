const BASE_URL = `${import.meta.env.VITE_API_URL}/api/sessions`

function authHeaders() {
  const token = localStorage.getItem('token')
  return {
    'Content-Type': 'application/json',
    ...(token ? { Authorization: `Bearer ${token}` } : {})
  }
}

export async function saveSession(payload) {
  const res = await fetch(BASE_URL, {
    method: 'POST',
    headers: authHeaders(),
    body: JSON.stringify(payload)
  })
  if (!res.ok) throw new Error('Erreur lors de la sauvegarde de la session')
  return res.json()
}

export async function getSessions() {
  const res = await fetch(BASE_URL, { headers: authHeaders() })
  if (!res.ok) throw new Error('Erreur lors de la récupération de l\'historique')
  return res.json()
}

export async function getSessionDetail(id) {
  const res = await fetch(`${BASE_URL}/${id}`, { headers: authHeaders() })
  if (!res.ok) throw new Error('Session introuvable')
  return res.json()
}
