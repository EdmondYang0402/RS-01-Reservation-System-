<script setup>
import { onMounted, ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { Calendar } from "@element-plus/icons-vue";
import { cancelReservation, getReservations } from "../services/reservation";
import { useI18n } from "vue-i18n";
import { formatDate, reservationStatusKey } from "../utils/i18n";

const reservations = ref([]);
const { t, locale } = useI18n();
const statusTypes = {
  CONFIRMED: "success",
  CHECKED_IN: "warning",
  CHECKED_OUT: "info",
  COMPLETED: "info",
  CANCELLED: "danger",
  NO_SHOW: "danger",
};
const statusType = (status) => statusTypes[status] || "info";
const load = async () => {
  reservations.value = await getReservations();
};
onMounted(load);
const cancel = async (item) => {
  await ElMessageBox.confirm(
    t("reservation.cancelConfirm", { no: item.reservationNo }),
    t("reservation.cancelTitle"),
    { type: "warning" },
  );
  await cancelReservation(item.reservationId);
  ElMessage.success(t("reservation.cancelled"));
  await load();
};
</script>

<template>
  <section class="page-hero compact">
    <div class="container">
      <span class="eyebrow">{{ t("reservation.eyebrow") }}</span>
      <h1>{{ t("reservation.my") }}</h1>
      <p>{{ t("reservation.myDescription") }}</p>
    </div>
  </section>
  <section class="section">
    <div class="container narrow">
      <div class="reservation-list">
        <article
          v-for="item in reservations"
          :key="item.reservationId"
          class="reservation-card"
        >
          <div class="reservation-icon">
            <el-icon><Calendar /></el-icon>
          </div>
          <div class="reservation-main">
            <div class="reservation-heading">
              <div>
                <small
                  >{{ t("reservation.reservationNo") }}
                  {{ item.reservationNo }}</small
                >
                <h2>{{ item.roomTypeName }}</h2>
              </div>
              <el-tag :type="statusType(item.status)" effect="light" round>{{
                t(reservationStatusKey(item.status))
              }}</el-tag>
            </div>
            <div class="reservation-info">
              <span
                ><small>{{ t("reservation.checkIn") }}</small
                >{{ formatDate(item.checkInDate, locale) }}</span
              ><i>→</i
              ><span
                ><small>{{ t("reservation.checkOut") }}</small
                >{{ formatDate(item.checkOutDate, locale) }}</span
              ><span class="reservation-total"
                ><small>{{ t("reservation.totalAmount") }}</small
                >¥{{ item.totalAmount }}</span
              >
            </div>
            <div class="reservation-actions">
              <RouterLink :to="`/reservations/${item.reservationId}`"
                ><el-button plain>{{
                  t("common.details")
                }}</el-button></RouterLink
              ><el-button
                v-if="item.status === 'CONFIRMED'"
                plain
                type="danger"
                @click="cancel(item)"
                >{{ t("reservation.cancel") }}</el-button
              >
            </div>
          </div>
        </article>
      </div>
    </div>
  </section>
</template>
