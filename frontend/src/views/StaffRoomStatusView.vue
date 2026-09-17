<script setup>
import { computed, onMounted, ref } from 'vue'
import { getStaffRoomStatus } from '../services/hotel'

const rooms = ref([])
const filter = ref('ALL')
const options = ['ALL', 'AVAILABLE', 'OCCUPIED', 'OUT_OF_SERVICE']
const labels = { ALL: '全部', AVAILABLE: '可用', OCCUPIED: '已入住', OUT_OF_SERVICE: '停用' }
const filtered = computed(() => filter.value === 'ALL' ? rooms.value : rooms.value.filter((room) => room.status === filter.value))
onMounted(async () => { rooms.value = (await getStaffRoomStatus()).data })
</script>

<template>
  <section class="staff-page"><div class="container"><div class="staff-heading"><div><span class="eyebrow">ROOM OPERATIONS</span><h1>今日房态</h1><p>前台房间状态概览 · Mock 数据</p></div><div class="status-summary"><span v-for="status in options.slice(1)" :key="status" :class="`dot-${status.toLowerCase()}`"><i></i>{{ labels[status] }} {{ rooms.filter(r => r.status === status).length }}</span></div></div>
    <div class="status-filters"><button v-for="option in options" :key="option" :class="{ active: filter === option }" @click="filter = option">{{ labels[option] }}</button></div>
    <div class="status-grid"><article v-for="room in filtered" :key="room.roomNumber" :class="['status-card', room.status.toLowerCase()]" ><span class="status-dot"></span><small>ROOM</small><strong>{{ room.roomNumber }}</strong><p>{{ room.roomType }}</p><em>{{ room.status }}</em></article></div>
  </div></section>
</template>
