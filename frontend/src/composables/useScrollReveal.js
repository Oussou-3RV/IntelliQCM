import { ref, onMounted, onUnmounted } from 'vue'

export function useScrollReveal(options = {}) {
  const { threshold = 0.15, once = true } = options
  const el = ref(null)
  const isVisible = ref(false)

  let observer = null

  onMounted(() => {
    observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          isVisible.value = true
          if (once) observer.disconnect()
        } else if (!once) {
          isVisible.value = false
        }
      },
      { threshold }
    )
    if (el.value) observer.observe(el.value)
  })

  onUnmounted(() => observer?.disconnect())

  return { el, isVisible }
}
