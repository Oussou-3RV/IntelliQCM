<template>
  <div class="flex items-center justify-between">
    <span class="flex items-center gap-1.5 text-gray-400 text-sm">
      <Timer :size="14" /> Temps restant
    </span>
    <span
      class="text-lg font-black tabular-nums transition-colors"
      :class="remaining <= 5 ? 'text-red-400' : remaining <= 10 ? 'text-yellow-400' : 'text-white'"
    >
      {{ formatted }}
    </span>
  </div>
  <div class="h-1.5 bg-gray-700 rounded-full overflow-hidden">
    <div
      class="h-full rounded-full transition-all duration-1000 ease-linear"
      :class="remaining <= 5 ? 'bg-red-500' : remaining <= 10 ? 'bg-yellow-500' : 'bg-indigo-500'"
      :style="{ width: progressPercent + '%' }"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, watch } from 'vue'
import { Timer } from 'lucide-vue-next'

const props = defineProps({
  seconds: { type: Number, required: true }
})

const emit = defineEmits(['timeout'])

const remaining = ref(props.seconds)
let interval = null

const formatted = computed(() => {
  const m = Math.floor(remaining.value / 60)
  const s = remaining.value % 60
  return m > 0
    ? `${m}:${String(s).padStart(2, '0')}`
    : `${remaining.value}s`
})

const progressPercent = computed(() =>
  Math.round((remaining.value / props.seconds) * 100)
)

function start() {
  clearInterval(interval)
  remaining.value = props.seconds
  interval = setInterval(() => {
    remaining.value--
    if (remaining.value <= 0) {
      clearInterval(interval)
      emit('timeout')
    }
  }, 1000)
}

// Redémarre le timer à chaque changement de durée (nouvelle question)
watch(() => props.seconds, () => start(), { immediate: false })

onMounted(() => start())
onUnmounted(() => clearInterval(interval))

// Exposer reset pour que le parent puisse relancer
defineExpose({ reset: start })
</script>
