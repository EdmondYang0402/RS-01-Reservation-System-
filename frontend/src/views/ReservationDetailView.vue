<script setup>
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { getReservation } from '../services/reservation'
import { useI18n } from 'vue-i18n'
import { formatDate, reservationStatusKey } from '../utils/i18n'

const route = useRoute()
const detail = ref(null)
const { t, locale } = useI18n()
onMounted(async () => { detail.value = await getReservation(route.params.id) })
</script>

<template><section class="management-page"><div v-if="detail" class="container narrow"><div class="management-heading"><div><span class="eyebrow">RESERVATION</span><h1>{{ t('reservation.detail') }}</h1></div><el-tag>{{ t(reservationStatusKey(detail.status)) }}</el-tag></div><el-descriptions :column="2" border><el-descriptions-item :label="t('reservation.number')">{{ detail.reservationNo }}</el-descriptions-item><el-descriptions-item :label="t('room.roomType')">{{ detail.roomTypeName }}</el-descriptions-item><el-descriptions-item :label="t('reservation.guest')">{{ detail.guestName }}</el-descriptions-item><el-descriptions-item :label="t('reservation.phone')">{{ detail.guestPhone }}</el-descriptions-item><el-descriptions-item :label="t('reservation.checkInDate')">{{ formatDate(detail.checkInDate, locale) }}</el-descriptions-item><el-descriptions-item :label="t('reservation.checkOutDate')">{{ formatDate(detail.checkOutDate, locale) }}</el-descriptions-item><el-descriptions-item :label="t('reservation.assignedRoom')">{{ detail.assignedRoomNumber || t('common.unassigned') }}</el-descriptions-item><el-descriptions-item :label="t('reservation.totalAmount')">¥{{ detail.totalAmount }}</el-descriptions-item></el-descriptions><h2 class="subsection-title">{{ t('reservation.nightlyDetails') }}</h2><el-table :data="detail.nights || []"><el-table-column :label="t('common.date')"><template #default="scope">{{ formatDate(scope.row.stayDate, locale) }}</template></el-table-column><el-table-column prop="price" :label="t('common.price')" /></el-table></div></section></template>
