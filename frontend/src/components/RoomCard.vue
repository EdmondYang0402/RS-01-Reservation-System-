<script setup>
import { User, House, ArrowRight } from '@element-plus/icons-vue'
defineProps({ room: { type: Object, required: true } })
</script>

<template>
  <article class="room-card">
    <div class="room-card-image"><img :src="room.image" :alt="room.name" /><span class="room-stock">仅余 {{ room.remaining }} 间</span></div>
    <div class="room-card-body">
      <div><span class="room-en">{{ room.subtitle }}</span><h3>{{ room.name }}</h3></div>
      <div class="room-meta"><span><el-icon><User /></el-icon>{{ room.capacity }} 人</span><span><el-icon><House /></el-icon>{{ room.bedType }}</span></div>
      <div class="room-card-footer"><p><small>每晚</small><strong>¥{{ room.price }}</strong><small>起</small></p><RouterLink :to="`/rooms/${room.id}`">查看详情 <el-icon><ArrowRight /></el-icon></RouterLink></div>
    </div>
  </article>
</template>
