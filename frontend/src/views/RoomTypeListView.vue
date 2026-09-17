<script setup>
import { onMounted, ref } from 'vue'
import SearchPanel from '../components/SearchPanel.vue'
import RoomCard from '../components/RoomCard.vue'
import { getRoomTypes } from '../services/hotel'

const rooms = ref([])
const loading = ref(true)
onMounted(async () => { rooms.value = (await getRoomTypes()).data; loading.value = false })
</script>

<template>
  <section class="page-hero"><div class="container"><span class="eyebrow">OUR ROOMS</span><h1>客房一览</h1><p>选择适合你的空间，在柔和晨光里醒来。</p></div></section>
  <section class="section"><div class="container"><SearchPanel /><div v-loading="loading" class="room-grid list-room-grid"><RoomCard v-for="room in rooms" :key="room.id" :room="room" /></div></div></section>
</template>
