<script setup>
import { User, House, ArrowRight } from "@element-plus/icons-vue";
import { useI18n } from "vue-i18n";
defineProps({ room: { type: Object, required: true } });
const { t } = useI18n();
</script>

<template>
  <article class="room-card">
    <div class="room-card-image">
      <img :src="room.image" :alt="room.name" /><span class="room-stock">{{
        t("room.remaining", { count: room.remaining })
      }}</span>
    </div>
    <div class="room-card-body">
      <div>
        <span class="room-en">{{
          room.subtitle || t("room.availableStay")
        }}</span>
        <h3>{{ room.name }}</h3>
      </div>
      <div class="room-meta">
        <span
          ><el-icon><User /></el-icon
          >{{ t("room.guestUnit", { count: room.capacity }) }}</span
        ><span
          ><el-icon><House /></el-icon>{{ room.bedType }}</span
        >
      </div>
      <div class="room-card-footer">
        <p>
          <small>{{ t("room.perNight") }}</small
          ><strong>¥{{ room.price }}</strong
          ><small>{{ t("room.from") }}</small>
        </p>
        <RouterLink :to="`/rooms/${room.id}`"
          >{{ t("common.details") }} <el-icon><ArrowRight /></el-icon
        ></RouterLink>
      </div>
    </div>
  </article>
</template>
