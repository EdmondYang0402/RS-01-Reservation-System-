<script setup>
import { ref } from 'vue'
import { Menu, Close } from '@element-plus/icons-vue'

const menuOpen = ref(false)
const links = [
  { to: '/', label: '首页' }, { to: '/rooms', label: '客房' },
  { to: '/reservations', label: '我的预订' }, { to: '/staff/rooms', label: '房态' },
]
</script>

<template>
  <header class="site-header">
    <div class="header-inner container">
      <RouterLink class="brand" to="/" @click="menuOpen = false">
        <span class="brand-mark">月</span><span><strong>栖月酒店</strong><small>TSUKIMI STAY</small></span>
      </RouterLink>
      <button class="nav-toggle" aria-label="切换导航" @click="menuOpen = !menuOpen">
        <el-icon><Close v-if="menuOpen" /><Menu v-else /></el-icon>
      </button>
      <nav :class="['main-nav', { open: menuOpen }]">
        <RouterLink v-for="link in links" :key="link.to" :to="link.to" @click="menuOpen = false">{{ link.label }}</RouterLink>
      </nav>
    </div>
  </header>
</template>
