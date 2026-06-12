<template>
  <div class="min-h-screen bg-gray-950 text-white">
    <LandingNavbar />

    <!-- Hero — fond sombre avec glow indigo -->
    <div class="relative overflow-hidden bg-gray-950">
      <div class="absolute inset-0 pointer-events-none">
        <div class="absolute top-[-120px] left-1/2 -translate-x-1/2 w-[700px] h-[500px] bg-indigo-600/10 rounded-full blur-3xl" />
        <div class="absolute top-[60px] left-1/4 w-[300px] h-[300px] bg-indigo-800/8 rounded-full blur-2xl" />
      </div>
      <LandingHero />
    </div>

    <!-- Features — fond légèrement plus clair -->
    <div class="bg-gray-900">
      <LandingFeatures />
    </div>

    <!-- Pricing — retour au fond sombre -->
    <div class="bg-gray-950">
      <LandingPricing />
    </div>

    <!-- CTA — dégradé indigo subtil -->
    <div class="relative overflow-hidden bg-gray-900">
      <div class="absolute inset-0 pointer-events-none">
        <div class="absolute bottom-0 left-1/2 -translate-x-1/2 w-[500px] h-[300px] bg-indigo-600/8 rounded-full blur-3xl" />
      </div>
      <LandingCTA />
    </div>

    <div class="bg-gray-950">
      <LandingFooter />
    </div>
  </div>
</template>

<script setup>
import LandingNavbar from '@/components/landing/LandingNavbar.vue'
import LandingHero from '@/components/landing/LandingHero.vue'
import LandingFeatures from '@/components/landing/LandingFeatures.vue'
import LandingPricing from '@/components/landing/LandingPricing.vue'
import LandingCTA from '@/components/landing/LandingCTA.vue'
import LandingFooter from '@/components/landing/LandingFooter.vue'
</script>
