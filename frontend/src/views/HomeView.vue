<script setup>
import { onMounted, ref } from 'vue'
import heroImage from '../assets/images/hero-hotel.png'
import SearchPanel from '../components/SearchPanel.vue'
import SectionHeading from '../components/SectionHeading.vue'
import RoomCard from '../components/RoomCard.vue'
import { getRoomTypes } from '../services/hotel'

const rooms = ref([])
onMounted(async () => { rooms.value = (await getRoomTypes()).data })
</script>

<template>
  <section class="hero">
    <img :src="heroImage" alt="栖月酒店庭院客房" />
    <div class="hero-overlay"></div>
    <div class="container hero-content"><span class="eyebrow">A QUIET STAY IN THE CITY</span><h1>留一晚给<br />温柔的月色</h1><p>日式留白与现代舒适相遇，回到旅途里安静的一隅。</p></div>
    <div class="container hero-search"><SearchPanel /></div>
  </section>
  <section class="section home-rooms">
    <div class="container">
      <SectionHeading eyebrow="STAY WITH US" title="为每一种旅途，留一间好房" description="自然光、柔软织物与安静庭院，构成恰到好处的休息空间。" />
      <div class="room-grid"><RoomCard v-for="room in rooms" :key="room.id" :room="room" /></div>
      <div class="section-action"><RouterLink class="text-link" to="/rooms">查看全部房型 →</RouterLink></div>
    </div>
  </section>
  <section class="promise-section"><div class="container promise-grid"><div><span>01</span><h3>静谧选址</h3><p>远离喧闹，也不离城市便利。</p></div><div><span>02</span><h3>细致睡眠</h3><p>亲肤寝具与柔和照明伴你入眠。</p></div><div><span>03</span><h3>温暖早餐</h3><p>用当季食材开启从容早晨。</p></div></div></section>
</template>
