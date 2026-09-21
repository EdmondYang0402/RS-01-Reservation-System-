<script setup>
import { onMounted, ref } from 'vue'
import heroImage from '../assets/images/hero-hotel.png'
import SearchPanel from '../components/SearchPanel.vue'
import SectionHeading from '../components/SectionHeading.vue'
import RoomCard from '../components/RoomCard.vue'
import { getAvailability } from '../services/inventory'
import { useSearchStore } from '../stores/search'
import { useI18n } from 'vue-i18n'

const rooms = ref([])
const search = useSearchStore()
const { t, tm } = useI18n()
onMounted(async () => {
  rooms.value = await getAvailability({ checkInDate: search.checkIn, checkOutDate: search.checkOut, guestCount: search.guests })
})
</script>

<template>
  <section class="hero">
    <img :src="heroImage" :alt="t('home.heroAlt')" />
    <div class="hero-overlay"></div>
    <div class="container hero-content"><span class="eyebrow">{{ t('home.heroEyebrow') }}</span><h1 class="pre-line">{{ t('home.heroTitle') }}</h1><p>{{ t('home.heroDescription') }}</p></div>
    <div class="container hero-search"><SearchPanel /></div>
  </section>
  <section class="section home-rooms">
    <div class="container">
      <SectionHeading :eyebrow="t('home.roomsEyebrow')" :title="t('home.roomsTitle')" :description="t('home.roomsDescription')" />
      <div class="room-grid"><RoomCard v-for="room in rooms" :key="room.id" :room="room" /></div>
      <div class="section-action"><RouterLink class="text-link" to="/rooms">{{ t('home.viewAllRooms') }}</RouterLink></div>
    </div>
  </section>
  <section class="promise-section"><div class="container promise-grid"><div v-for="(item, index) in tm('home.promises')" :key="item.title"><span>0{{ index + 1 }}</span><h3>{{ item.title }}</h3><p>{{ item.description }}</p></div></div></section>
</template>
