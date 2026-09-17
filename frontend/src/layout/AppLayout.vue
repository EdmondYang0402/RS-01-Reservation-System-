<script setup>
import AppHeader from '../components/AppHeader.vue'
import AppFooter from '../components/AppFooter.vue'
</script>

<template>
  <div class="app-shell">
    <AppHeader />
    <main><RouterView /></main>
    <AppFooter />
  </div>
</template>
