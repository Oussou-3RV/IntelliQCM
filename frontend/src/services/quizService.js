const BASE_URL = 'http://localhost:8080/api/quiz'

export async function generateQuiz(courseContent, questionCount, difficulty) {
  const response = await fetch(`${BASE_URL}/generate`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ courseContent, questionCount, difficulty })
  })

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