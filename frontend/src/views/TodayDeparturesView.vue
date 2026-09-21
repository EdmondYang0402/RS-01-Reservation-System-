<script setup>
import { onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import CheckOutDialog from '../components/CheckOutDialog.vue'
import { getTodayDepartures } from '../services/reservation'
import { formatDate, reservationStatusKey } from '../utils/i18n'

const rows = ref([])
const loading = ref(false)
const dialogOpen = ref(false)
const selected = ref(null)
const { t, locale } = useI18n()
const load = async () => { loading.value = true; try { rows.value = await getTodayDepartures() } finally { loading.value = false } }
const openCheckOut = (row) => { selected.value = row; dialogOpen.value = true }
onMounted(load)
</script>

<template><section class="management-page"><div class="container"><div class="management-heading"><div><span class="eyebrow">FRONT DESK</span><h1>{{ t('frontDesk.departures') }}</h1></div><el-button @click="load">{{ t('common.refresh') }}</el-button></div><el-table v-loading="loading" :data="rows"><el-table-column prop="reservationNo" :label="t('reservation.number')" min-width="170" /><el-table-column prop="guestName" :label="t('reservation.guest')" /><el-table-column prop="roomTypeName" :label="t('room.roomType')" min-width="150" /><el-table-column :label="t('reservation.checkInDate')"><template #default="scope">{{ formatDate(scope.row.checkInDate, locale) }}</template></el-table-column><el-table-column :label="t('reservation.checkOutDate')"><template #default="scope">{{ formatDate(scope.row.checkOutDate, locale) }}</template></el-table-column><el-table-column :label="t('frontDesk.assignedRoom')"><template #default="scope">{{ scope.row.assignedRoomNumber || t('common.unassigned') }}</template></el-table-column><el-table-column :label="t('common.status')"><template #default="scope">{{ t(reservationStatusKey(scope.row.status)) }}</template></el-table-column><el-table-column :label="t('common.actions')"><template #default="scope"><el-button link type="primary" @click="openCheckOut(scope.row)">{{ t('frontDesk.checkOut') }}</el-button></template></el-table-column></el-table><CheckOutDialog v-model="dialogOpen" :reservation="selected" @success="load" /></div></section></template>
