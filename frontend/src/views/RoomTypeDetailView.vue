<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { Check, User, House } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getRoomTypeDetail } from '../services/hotel'
import { useSearchStore } from '../stores/search'

const route = useRoute()
const search = useSearchStore()
const room = ref(null)
const nights = computed(() => Math.max(1, Math.round((new Date(search.checkOut) - new Date(search.checkIn)) / 86400000) || 1))
const total = computed(() => room.value ? room.value.price * nights.value : 0)
onMounted(async () => { room.value = (await getRoomTypeDetail(route.params.id)).data })
const reserve = () => ElMessage.success('V1 为前端演示，预订功能将在接入 API 后开放')
</script>

<template>
  <div v-if="room" class="detail-page">
    <section class="detail-visual"><img :src="room.image" :alt="room.name" /><div class="container detail-title"><span>{{ room.subtitle }}</span><h1>{{ room.name }}</h1></div></section>
    <section class="section"><div class="container detail-layout">
      <div class="detail-copy"><div class="detail-facts"><span><el-icon><User /></el-icon>{{ room.capacity }} 位客人</span><span><el-icon><House /></el-icon>{{ room.bedType }}</span><span>{{ room.size }} m²</span></div><h2>房间介绍</h2><p>{{ room.description }}</p><h2>客房设施</h2><div class="facility-grid"><span v-for="item in room.facilities" :key="item"><el-icon><Check /></el-icon>{{ item }}</span></div></div>
      <aside class="booking-card"><div class="booking-price"><span>每晚</span><strong>¥{{ room.price }}</strong><small>含税</small></div><label>入住日期<el-date-picker v-model="search.checkIn" type="date" value-format="YYYY-MM-DD" /></label><label>退房日期<el-date-picker v-model="search.checkOut" type="date" value-format="YYYY-MM-DD" /></label><div class="booking-line"><span>{{ nights }} 晚住宿</span><span>¥{{ total }}</span></div><div class="booking-total"><span>合计</span><strong>¥{{ total }}</strong></div><p class="stock-note">当前日期仅余 {{ room.remaining }} 间</p><el-button type="primary" size="large" @click="reserve">预订此房型</el-button></aside>
    </div></section>
  </div>
</template>
