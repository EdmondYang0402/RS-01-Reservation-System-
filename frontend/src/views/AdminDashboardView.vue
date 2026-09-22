<script setup>
import { onMounted, ref } from "vue";
import { useI18n } from "vue-i18n";
import { getHotels, getAdminRoomTypes, getAdminRooms } from "../services/admin";

const { t } = useI18n();
const loading = ref(false);
const stats = ref({ hotels: 0, roomTypes: 0, rooms: 0, available: 0 });
onMounted(async () => {
  loading.value = true;
  try {
    const [hotels, roomTypes, rooms] = await Promise.all([
      getHotels({ offset: 0, limit: 100 }),
      getAdminRoomTypes({ hotelId: 1, offset: 0, limit: 100 }),
      getAdminRooms({ hotelId: 1, offset: 0, limit: 100 }),
    ]);
    stats.value = {
      hotels: hotels.length,
      roomTypes: roomTypes.length,
      rooms: rooms.length,
      available: rooms.filter((room) => room.status === "AVAILABLE").length,
    };
  } finally {
    loading.value = false;
  }
});
</script>

<template>
  <section class="management-page">
    <div class="container" v-loading="loading">
      <div class="management-heading">
        <div>
          <span class="eyebrow">{{ t("admin.title") }}</span>
          <h1>{{ t("admin.dashboard") }}</h1>
        </div>
      </div>
      <div class="metric-grid">
        <el-card shadow="never"
          ><small>{{ t("admin.hotelCount") }}</small
          ><strong>{{ stats.hotels }}</strong></el-card
        >
        <el-card shadow="never"
          ><small>{{ t("admin.roomTypeCount") }}</small
          ><strong>{{ stats.roomTypes }}</strong></el-card
        >
        <el-card shadow="never"
          ><small>{{ t("admin.roomCount") }}</small
          ><strong>{{ stats.rooms }}</strong></el-card
        >
        <el-card shadow="never"
          ><small>{{ t("admin.availableRoomCount") }}</small
          ><strong>{{ stats.available }}</strong></el-card
        >
      </div>
    </div>
  </section>
</template>
