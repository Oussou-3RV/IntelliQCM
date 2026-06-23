const BASE_URL = `${import.meta.env.VITE_API_URL}/api/quiz`

function authHeaders() {
  const token = localStorage.getItem('token')
  return {
    'Content-Type': 'application/json',
    ...(token ? { Authorization: `Bearer ${token}` } : {})
  }
}

export async function generateQuiz(courseContent, questionCount, difficulty) {
  const response = await fetch(`${BASE_URL}/generate`, {
    method: 'POST',
    headers: authHeaders(),
    body: JSON.stringify({ courseContent, questionCount, difficulty })
  })

  if (response.status === 429) {
    const err = new Error('quota')
    err.status = 429
    throw err
  }

  if (!response.ok) throw new Error('Erreur lors de la génération du quiz')
  return response.json()
}

export async function generateAdvice(results, difficulty, totalQuestions) {
  const response = await fetch(`${BASE_URL}/advice`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ results, difficulty, totalQuestions })
  })

  if (!response.ok) throw new Error('Erreur lors de la génération du conseil')
  return response.text()
}