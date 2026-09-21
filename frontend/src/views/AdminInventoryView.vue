<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getAdminInventory, initializeInventory } from '../services/inventory'
import { useI18n } from 'vue-i18n'
import { formatDate } from '../utils/i18n'

const query = reactive({ roomTypeId: 1, startDate: '', endDate: '' })
const rows = ref([])
const loading = ref(false)
const dialogOpen = ref(false)
const editForm = reactive({ roomTypeId: null, stayDate: '', endDate: '' })
const { t, locale } = useI18n()
const load = async () => { loading.value = true; try { rows.value = await getAdminInventory(query) } finally { loading.value = false } }
const openEditor = () => { Object.assign(editForm, { roomTypeId: query.roomTypeId, stayDate: query.startDate, endDate: query.endDate }); dialogOpen.value = true }
const submitInitialize = async () => {
  const count = await initializeInventory({ roomTypeId: editForm.roomTypeId, startDate: editForm.stayDate, endDate: editForm.endDate })
  ElMessage.success(t('admin.initializeSuccess', { count }))
  dialogOpen.value = false
  await load()
}
</script>

<template><section class="management-page"><div class="container"><div class="management-heading"><div><span class="eyebrow">ADMIN</span><h1>{{ t('admin.inventory') }}</h1></div><el-button @click="openEditor()">{{ t('admin.initializeInventory') }}</el-button></div><el-form class="query-form" inline><el-form-item :label="t('room.roomTypeId')"><el-input-number v-model="query.roomTypeId" :min="1" /></el-form-item><el-form-item :label="t('admin.startDate')"><el-date-picker v-model="query.startDate" type="date" value-format="YYYY-MM-DD" /></el-form-item><el-form-item :label="t('admin.endDate')"><el-date-picker v-model="query.endDate" type="date" value-format="YYYY-MM-DD" /></el-form-item><el-form-item><el-button type="primary" @click="load">{{ t('common.search') }}</el-button></el-form-item></el-form><el-table v-loading="loading" :data="rows"><el-table-column :label="t('common.date')"><template #default="scope">{{ formatDate(scope.row.stayDate, locale) }}</template></el-table-column><el-table-column prop="roomTypeId" :label="t('room.roomTypeId')" /><el-table-column prop="totalInventory" :label="t('admin.totalInventory')" /><el-table-column prop="reservedCount" :label="t('admin.reservedCount')" /><el-table-column prop="outOfServiceCount" :label="t('admin.outOfServiceCount')" /></el-table><el-dialog v-model="dialogOpen" :title="t('admin.initializeTitle')" width="560"><el-alert :title="t('admin.initializeNotice')" type="warning" :closable="false" /><el-form label-position="top" class="dialog-form"><div class="form-grid"><el-form-item :label="t('room.roomTypeId')"><el-input-number v-model="editForm.roomTypeId" :min="1" /></el-form-item><el-form-item :label="t('admin.startDate')"><el-date-picker v-model="editForm.stayDate" type="date" value-format="YYYY-MM-DD" /></el-form-item><el-form-item :label="t('admin.endDate')"><el-date-picker v-model="editForm.endDate" type="date" value-format="YYYY-MM-DD" /></el-form-item></div></el-form><template #footer><el-button @click="dialogOpen = false">{{ t('common.cancel') }}</el-button><el-button type="primary" :disabled="!editForm.roomTypeId || !editForm.stayDate || !editForm.endDate" @click="submitInitialize">{{ t('common.submit') }}</el-button></template></el-dialog></div></section></template>
