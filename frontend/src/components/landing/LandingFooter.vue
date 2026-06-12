<template>
  <footer class="border-t border-gray-800 px-6 py-10">
    <div class="max-w-5xl mx-auto space-y-6">

      <div class="flex flex-col sm:flex-row items-center justify-between gap-6">
        <!-- Logo -->
        <div class="flex items-center gap-2">
          <GraduationCap :size="26" class="text-indigo-400" />
          <span class="text-white font-black text-lg tracking-tight">IntelliQCM</span>
        </div>

        <!-- Liens -->
        <div class="flex items-center gap-6 text-sm text-gray-500">
          <a href="#" class="hover:text-gray-300 transition-colors flex items-center gap-1.5">
            <Shield :size="13" /> Confidentialité
          </a>
          <a href="#" class="hover:text-gray-300 transition-colors flex items-center gap-1.5">
            <FileText :size="13" /> Conditions
          </a>
          <a href="#" class="hover:text-gray-300 transition-colors flex items-center gap-1.5">
            <Mail :size="13" /> Contact
          </a>
        </div>
      </div>

      <div class="border-t border-gray-800 pt-6 flex flex-col sm:flex-row items-center justify-between gap-4 text-xs text-gray-600">
        <p>© 2026 IntelliQCM. Tous droits réservés.</p>
        <p class="flex items-center gap-1.5">
          Propulsé par <span class="text-gray-500 font-medium">GPT-4o mini</span>
        </p>
      </div>

    </div>
  </footer>
</template>

<script setup>
import { Shield, FileText, Mail, Sparkles, GraduationCap } from 'lucide-vue-next'
</script>
