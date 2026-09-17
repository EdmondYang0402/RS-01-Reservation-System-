<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Calendar } from '@element-plus/icons-vue'
import { getMyReservations } from '../services/hotel'

const reservations = ref([])
const statusMap = { CONFIRMED: ['已确认', 'success'], COMPLETED: ['已完成', 'info'], CANCELLED: ['已取消', 'danger'] }
onMounted(async () => { reservations.value = (await getMyReservations()).data })
const cancel = () => ElMessage.info('取消按钮为 V1 占位，未执行任何业务操作')
</script>

<template>
  <section class="page-hero compact"><div class="container"><span class="eyebrow">MY STAYS</span><h1>我的预订</h1><p>查看你的入住计划与历史旅程。</p></div></section>
  <section class="section"><div class="container narrow"><div class="reservation-list">
    <article v-for="item in reservations" :key="item.id" class="reservation-card"><div class="reservation-icon"><el-icon><Calendar /></el-icon></div><div class="reservation-main"><div class="reservation-heading"><div><small>预订编号 {{ item.reservationNo }}</small><h2>{{ item.roomType }}</h2></div><el-tag :type="statusMap[item.status][1]" effect="light" round>{{ statusMap[item.status][0] }}</el-tag></div><div class="reservation-info"><span><small>入住</small>{{ item.checkIn }}</span><i>→</i><span><small>退房</small>{{ item.checkOut }}</span><span class="reservation-total"><small>订单总额</small>¥{{ item.totalAmount }}</span></div><el-button v-if="item.status === 'CONFIRMED'" plain @click="cancel">取消预订</el-button></div></article>
  </div></div></section>
</template>
