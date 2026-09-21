<script setup>
import { ref } from 'vue'
import { useI18n } from 'vue-i18n'
import { ElMessage } from 'element-plus'
import { checkOutReservation } from '../services/reservation'

const props = defineProps({ modelValue: Boolean, reservation: Object })
const emit = defineEmits(['update:modelValue', 'success'])
const { t } = useI18n()
const loading = ref(false)
const submit = async () => {
  loading.value = true
  try {
    await checkOutReservation(props.reservation.reservationId)
    ElMessage.success(t('common.requestSubmitted'))
    emit('update:modelValue', false)
    emit('success')
  } finally { loading.value = false }
}
</script>

<template>
  <el-dialog :model-value="modelValue" :title="t('frontDesk.checkOut')" width="440" @close="emit('update:modelValue', false)">
    <el-descriptions :column="1" border><el-descriptions-item :label="t('reservation.reservationNo')">{{ reservation?.reservationNo }}</el-descriptions-item><el-descriptions-item :label="t('reservation.guest')">{{ reservation?.guestName }}</el-descriptions-item><el-descriptions-item :label="t('frontDesk.assignedRoom')">{{ reservation?.assignedRoomNumber || t('common.unassigned') }}</el-descriptions-item></el-descriptions>
    <template #footer><el-button @click="emit('update:modelValue', false)">{{ t('common.cancel') }}</el-button><el-button type="primary" :loading="loading" @click="submit">{{ t('frontDesk.checkOut') }}</el-button></template>
  </el-dialog>
</template>
