<script setup>
import { reactive, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { useI18n } from 'vue-i18n'
import { checkInReservation, getAvailableRooms } from '../services/reservation'

const props = defineProps({ modelValue: Boolean, reservation: Object })
const emit = defineEmits(['update:modelValue', 'success'])
const loading = ref(false)
const roomLoading = ref(false)
const rooms = ref([])
const form = reactive({ roomId: null })
const { t } = useI18n()
watch(() => props.modelValue, async (open) => {
  if (!open) return
  form.roomId = null
  roomLoading.value = true
  try { rooms.value = await getAvailableRooms(props.reservation.roomTypeId) } finally { roomLoading.value = false }
})

const submit = async () => {
  loading.value = true
  try {
    await checkInReservation(props.reservation.reservationId, form.roomId)
    ElMessage.success(t('frontDesk.checkInSubmitted'))
    emit('update:modelValue', false)
    emit('success')
  } finally { loading.value = false }
}
</script>

<template>
  <el-dialog :model-value="modelValue" :title="t('frontDesk.checkIn')" width="440" @close="emit('update:modelValue', false)"><p class="dialog-note">{{ t('reservation.reservationNo') }}：{{ reservation?.reservationNo }}</p><el-form label-position="top"><el-form-item :label="t('frontDesk.availableRooms')"><el-select v-model="form.roomId" v-loading="roomLoading" :placeholder="t('frontDesk.noAvailableRoom')"><el-option v-for="room in rooms" :key="room.id" :label="room.roomNumber" :value="room.id" /></el-select></el-form-item></el-form><template #footer><el-button @click="emit('update:modelValue', false)">{{ t('common.cancel') }}</el-button><el-button type="primary" :disabled="!form.roomId" :loading="loading" @click="submit">{{ t('frontDesk.submitCheckIn') }}</el-button></template></el-dialog>
</template>
