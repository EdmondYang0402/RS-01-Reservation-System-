<script setup>
defineProps({ eyebrow: String, title: String, description: String })
</script>

<template>
  <div class="section-heading">
    <span class="eyebrow">{{ eyebrow }}</span><h2>{{ title }}</h2><p v-if="description">{{ description }}</p>
  </div>
</template>
