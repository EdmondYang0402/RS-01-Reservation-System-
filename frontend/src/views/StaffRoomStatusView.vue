<script setup>
import { computed, onMounted, ref } from "vue";
import { getRoomStatus } from "../services/room";
import { useI18n } from "vue-i18n";
import { roomStatusKey } from "../utils/i18n";

const rooms = ref([]);
const filter = ref("ALL");
const options = ["ALL", "AVAILABLE", "OCCUPIED", "OUT_OF_SERVICE"];
const { t } = useI18n();
const statusLabel = (status) =>
  status === "ALL" ? t("common.all") : t(roomStatusKey(status));
const filtered = computed(() =>
  filter.value === "ALL"
    ? rooms.value
    : rooms.value.filter((room) => room.status === filter.value),
);
onMounted(async () => {
  rooms.value = await getRoomStatus({ hotelId: 1, offset: 0, limit: 100 });
});
</script>

<template>
  <section class="staff-page">
    <div class="container">
      <div class="staff-heading">
        <div>
          <span class="eyebrow">{{ t("frontDesk.title") }}</span>
          <h1>{{ t("frontDesk.roomStatusTitle") }}</h1>
          <p>{{ t("frontDesk.roomStatusDescription") }}</p>
        </div>
        <div class="status-summary">
          <span
            v-for="status in options.slice(1)"
            :key="status"
            :class="`dot-${status.toLowerCase()}`"
            ><i></i>{{ statusLabel(status) }}
            {{ rooms.filter((r) => r.status === status).length }}</span
          >
        </div>
      </div>
      <div class="status-filters">
        <button
          v-for="option in options"
          :key="option"
          :class="{ active: filter === option }"
          @click="filter = option"
        >
          {{ statusLabel(option) }}
        </button>
      </div>
      <div class="status-grid">
        <article
          v-for="room in filtered"
          :key="room.roomNumber"
          :class="['status-card', room.status.toLowerCase()]"
        >
          <span class="status-dot"></span
          ><small>{{ t("frontDesk.roomLabel") }}</small
          ><strong>{{ room.roomNumber }}</strong>
          <p>{{ t("room.roomTypeId") }}：{{ room.roomTypeId }}</p>
          <em>{{ statusLabel(room.status) }}</em>
        </article>
      </div>
    </div>
  </section>
</template>
