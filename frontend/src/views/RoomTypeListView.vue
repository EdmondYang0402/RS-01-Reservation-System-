<script setup>
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import SearchPanel from "../components/SearchPanel.vue";
import RoomCard from "../components/RoomCard.vue";
import { getAvailability } from "../services/inventory";
import { useSearchStore } from "../stores/search";
import { useI18n } from "vue-i18n";

const route = useRoute();
const search = useSearchStore();
const rooms = ref([]);
const loading = ref(true);
const { t } = useI18n();
const load = async () => {
  const params = {
    checkInDate: route.query.checkInDate || search.checkIn,
    checkOutDate: route.query.checkOutDate || search.checkOut,
    guestCount: Number(route.query.guestCount || search.guests),
  };
  search.setSearch({
    checkIn: params.checkInDate,
    checkOut: params.checkOutDate,
    guests: params.guestCount,
  });
  loading.value = true;
  try {
    rooms.value = await getAvailability(params);
  } finally {
    loading.value = false;
  }
};
onMounted(load);
watch(() => route.query, load);
</script>

<template>
  <section class="page-hero">
    <div class="container">
      <span class="eyebrow">{{ t("room.listEyebrow") }}</span>
      <h1>{{ t("room.listTitle") }}</h1>
      <p>{{ t("room.listDescription") }}</p>
    </div>
  </section>
  <section class="section">
    <div class="container">
      <SearchPanel />
      <div v-loading="loading" class="room-grid list-room-grid">
        <RoomCard v-for="room in rooms" :key="room.roomTypeId" :room="room" />
      </div>
      <el-empty
        v-if="!loading && !rooms.length"
        :description="t('room.empty')"
      />
    </div>
  </section>
</template>
