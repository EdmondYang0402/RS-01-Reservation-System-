<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import { useSearchStore } from '../stores/search'

const router = useRouter()
const searchStore = useSearchStore()
const form = reactive({ checkIn: searchStore.checkIn, checkOut: searchStore.checkOut, guests: searchStore.guests })
const disabledBeforeTomorrow = (date) => date.getTime() < new Date().setHours(0, 0, 0, 0)
const submit = () => { searchStore.setSearch(form); router.push('/rooms') }
</script>

<template>
  <form class="search-panel" @submit.prevent="submit">
    <label><span>入住日期</span><el-date-picker v-model="form.checkIn" type="date" value-format="YYYY-MM-DD" format="YYYY / MM / DD" :disabled-date="disabledBeforeTomorrow" /></label>
    <label><span>退房日期</span><el-date-picker v-model="form.checkOut" type="date" value-format="YYYY-MM-DD" format="YYYY / MM / DD" :disabled-date="disabledBeforeTomorrow" /></label>
    <label><span>入住人数</span><el-select v-model="form.guests"><el-option v-for="n in 4" :key="n" :label="`${n} 位客人`" :value="n" /></el-select></label>
    <el-button native-type="submit" type="primary" :icon="Search">查询客房</el-button>
  </form>
</template>
