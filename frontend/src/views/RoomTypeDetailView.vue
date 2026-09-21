<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Check, User, House } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getAvailability } from '../services/inventory'
import { createReservation } from '../services/reservation'
import { useSearchStore } from '../stores/search'
import { useI18n } from 'vue-i18n'

const route = useRoute()
const router = useRouter()
const search = useSearchStore()
const room = ref(null)
const loading = ref(false)
const bookingOpen = ref(false)
const submitting = ref(false)
const booking = reactive({ guestName: '', guestPhone: '', guestCount: search.guests })
const { t } = useI18n()

const load = async () => {
  loading.value = true
  try {
    const rooms = await getAvailability({ checkInDate: search.checkIn, checkOutDate: search.checkOut, guestCount: search.guests })
    room.value = rooms.find((item) => item.roomTypeId === Number(route.params.id)) || null
  } finally { loading.value = false }
}

const reserve = async () => {
  submitting.value = true
  try {
    const reservation = await createReservation({
      roomTypeId: room.value.roomTypeId,
      checkInDate: search.checkIn,
      checkOutDate: search.checkOut,
      guestName: booking.guestName,
      guestPhone: booking.guestPhone,
      guestCount: booking.guestCount,
    })
    ElMessage.success(t('room.bookingCreated'))
    bookingOpen.value = false
    router.push(`/reservations/${reservation.reservationId}`)
  } finally { submitting.value = false }
}

onMounted(load)
</script>

<template>
  <div v-loading="loading" class="detail-page">
    <template v-if="room">
      <section class="detail-visual"><img :src="room.image" :alt="room.name" /><div class="container detail-title"><span>{{ room.subtitle }}</span><h1>{{ room.name }}</h1></div></section>
      <section class="section"><div class="container detail-layout">
        <div class="detail-copy"><div class="detail-facts"><span><el-icon><User /></el-icon>{{ t('room.guestUnit', { count: room.capacity }) }}</span><span><el-icon><House /></el-icon>{{ room.bedType }}</span><span>{{ t('room.nights', { count: room.nightCount }) }}</span></div><h2>{{ t('room.intro') }}</h2><p>{{ room.description || t('room.defaultDescription') }}</p><template v-if="room.facilities?.length"><h2>{{ t('room.facilities') }}</h2><div class="facility-grid"><span v-for="item in room.facilities" :key="item"><el-icon><Check /></el-icon>{{ item }}</span></div></template></div>
        <aside class="booking-card"><div class="booking-price"><span>{{ t('room.perNight') }}</span><strong>¥{{ room.basePrice }}</strong><small>{{ t('room.taxIncluded') }}</small></div><label>{{ t('search.checkInDate') }}<el-date-picker v-model="search.checkIn" type="date" value-format="YYYY-MM-DD" @change="load" /></label><label>{{ t('search.checkOutDate') }}<el-date-picker v-model="search.checkOut" type="date" value-format="YYYY-MM-DD" @change="load" /></label><div class="booking-line"><span>{{ t('room.nightStay', { count: room.nightCount }) }}</span><span>{{ t('room.backendPricing') }}</span></div><div class="booking-total"><span>{{ t('room.total') }}</span><strong>¥{{ room.totalAmount }}</strong></div><p class="stock-note">{{ t('room.availableCount', { count: room.availableRooms }) }}</p><el-button type="primary" size="large" @click="bookingOpen = true">{{ t('room.book') }}</el-button></aside>
      </div></section>
      <el-dialog v-model="bookingOpen" :title="t('room.bookingTitle')" width="500"><el-form label-position="top"><el-form-item :label="t('room.guestName')"><el-input v-model="booking.guestName" maxlength="100" /></el-form-item><el-form-item :label="t('room.phone')"><el-input v-model="booking.guestPhone" maxlength="32" /></el-form-item><el-form-item :label="t('search.guestCount')"><el-input-number v-model="booking.guestCount" :min="1" :max="room.capacity" /></el-form-item></el-form><template #footer><el-button @click="bookingOpen = false">{{ t('common.cancel') }}</el-button><el-button type="primary" :disabled="!booking.guestName || !booking.guestPhone" :loading="submitting" @click="reserve">{{ t('room.confirmBooking') }}</el-button></template></el-dialog>
    </template>
    <el-empty v-else-if="!loading" :description="t('room.unavailable')" />
  </div>
</template>
