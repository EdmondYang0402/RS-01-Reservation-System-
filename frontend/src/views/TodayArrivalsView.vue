<script setup>
import { onMounted, ref } from "vue";
import { useI18n } from "vue-i18n";
import { ElMessage, ElMessageBox } from "element-plus";
import CheckInDialog from "../components/CheckInDialog.vue";
import {
  getTodayArrivals,
  markReservationNoShow,
} from "../services/reservation";
import { formatDate, reservationStatusKey } from "../utils/i18n";

const rows = ref([]);
const loading = ref(false);
const dialogOpen = ref(false);
const selected = ref(null);
const { t, locale } = useI18n();
const load = async () => {
  loading.value = true;
  try {
    rows.value = await getTodayArrivals();
  } finally {
    loading.value = false;
  }
};
const openCheckIn = (row) => {
  selected.value = row;
  dialogOpen.value = true;
};
const markNoShow = async (row) => {
  await ElMessageBox.confirm(
    t("frontDesk.noShowConfirm", { no: row.reservationNo }),
    t("frontDesk.noShow"),
  );
  await markReservationNoShow(row.reservationId);
  ElMessage.success(t("common.requestSubmitted"));
  load();
};
onMounted(load);
</script>

<template>
  <section class="management-page">
    <div class="container">
      <div class="management-heading">
        <div>
          <span class="eyebrow">{{ t("frontDesk.title") }}</span>
          <h1>{{ t("frontDesk.arrivals") }}</h1>
        </div>
        <el-button @click="load">{{ t("common.refresh") }}</el-button>
      </div>
      <el-table v-loading="loading" :data="rows"
        ><el-table-column
          prop="reservationNo"
          :label="t('reservation.number')"
          min-width="170"
        /><el-table-column
          prop="guestName"
          :label="t('reservation.guest')"
        /><el-table-column
          prop="roomTypeName"
          :label="t('room.roomType')"
          min-width="150"
        /><el-table-column :label="t('reservation.checkInDate')"
          ><template #default="scope">{{
            formatDate(scope.row.checkInDate, locale)
          }}</template></el-table-column
        ><el-table-column :label="t('reservation.checkOutDate')"
          ><template #default="scope">{{
            formatDate(scope.row.checkOutDate, locale)
          }}</template></el-table-column
        ><el-table-column :label="t('common.status')"
          ><template #default="scope">{{
            t(reservationStatusKey(scope.row.status))
          }}</template></el-table-column
        ><el-table-column
          :label="t('common.actions')"
          fixed="right"
          min-width="150"
          ><template #default="scope"
            ><el-button link type="primary" @click="openCheckIn(scope.row)">{{
              t("frontDesk.checkIn")
            }}</el-button
            ><el-button link type="danger" @click="markNoShow(scope.row)">{{
              t("frontDesk.noShow")
            }}</el-button></template
          ></el-table-column
        ></el-table
      ><CheckInDialog
        v-model="dialogOpen"
        :reservation="selected"
        @success="load"
      />
    </div>
  </section>
</template>
