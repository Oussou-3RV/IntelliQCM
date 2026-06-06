<template>
  <div class="min-h-screen bg-gray-900 text-white">
    <LandingNavbar />
    <LandingHero />
    <LandingFeatures />
    <LandingPricing />
    <LandingCTA />
    <LandingFooter />
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
