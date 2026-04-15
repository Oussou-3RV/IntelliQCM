<template>
    <div
      class="border-2 border-dashed rounded-2xl p-10 text-center transition-all duration-200 cursor-pointer"
      :class="isDragging
        ? 'border-indigo-500 bg-indigo-500/10'
        : 'border-gray-700 bg-gray-800/50 hover:border-indigo-400'"
      @dragover.prevent="isDragging = true"
      @dragleave="isDragging = false"
      @drop.prevent="onDrop"
      @click="triggerInput"
    >
      <!-- Pas encore de fichier -->
      <div v-if="!content">
        <div class="text-5xl mb-4">{{ isDragging ? '📂' : '📄' }}</div>
        <p class="text-white font-semibold text-lg mb-1">Dépose ton cours ici</p>
        <p class="text-gray-400 text-sm">PDF ou TXT · Glisse-dépose ou clique</p>
        <input
          ref="fileInput"
          type="file"
          accept=".pdf,.txt"
          class="hidden"
          @change="onFileChange"
        />
      </div>
  
      <!-- Fichier chargé -->
      <div v-else @click.stop>
        <div class="flex items-center justify-between mb-3">
          <div class="flex items-center gap-2">
            <span class="text-green-400 text-xl">✓</span>
            <span class="text-white font-medium">{{ fileName }}</span>
          </div>
          <button
            class="text-sm text-gray-400 hover:text-white bg-gray-700 px-3 py-1 rounded-full transition"
            @click="reset"
          >
            Changer
          </button>
        </div>
        <p class="text-gray-400 text-sm text-left leading-relaxed line-clamp-3">
          {{ content.slice(0, 200) }}...
        </p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  
  const emit = defineEmits(['content-ready'])
  
  const isDragging = ref(false)
  const content = ref('')
  const fileName = ref('')
  const fileInput = ref(null)
  
  function triggerInput() {
    if (!content.value) fileInput.value.click()
  }
  
  async function onFileChange(e) {
    const file = e.target.files[0]
    if (file) await readFile(file)
  }
  
  async function onDrop(e) {
    isDragging.value = false
    const file = e.dataTransfer.files[0]
    if (file) await readFile(file)
  }
  
  async function readFile(file) {
    fileName.value = file.name
    if (file.type === 'application/pdf') {
      content.value = await extractPdfText(file)
    } else {
      content.value = await file.text()
    }
    emit('content-ready', content.value)
  }
  
  async function extractPdfText(file) {
    if (!window.pdfjsLib) {
      await loadScript('https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.min.js')
      window.pdfjsLib.GlobalWorkerOptions.workerSrc =
        'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js'
    }
    const buffer = await file.arrayBuffer()
    const pdf = await window.pdfjsLib.getDocument({ data: buffer }).promise
    let text = ''
    for (let i = 1; i <= pdf.numPages; i++) {
      const page = await pdf.getPage(i)
      const pageContent = await page.getTextContent()
      text += pageContent.items.map(s => s.str).join(' ') + '\n'
    }
    return text
  }
  
  function loadScript(src) {
    return new Promise((resolve, reject) => {
      const s = document.createElement('script')
      s.src = src
      s.onload = resolve
      s.onerror = reject
      document.head.appendChild(s)
    })
  }
  
  function reset() {
    content.value = ''
    fileName.value = ''
    if (fileInput.value) fileInput.value.value = ''
    emit('content-ready', '')
  }
  </script>