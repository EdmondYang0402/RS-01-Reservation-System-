<script setup>
import { reactive } from "vue";
import { useRouter } from "vue-router";
import { Search } from "@element-plus/icons-vue";
import { useI18n } from "vue-i18n";
import { useSearchStore } from "../stores/search";

const router = useRouter();
const searchStore = useSearchStore();
const { t } = useI18n();
const form = reactive({
  checkIn: searchStore.checkIn,
  checkOut: searchStore.checkOut,
  guests: searchStore.guests,
});
const disabledBeforeTomorrow = (date) =>
  date.getTime() < new Date().setHours(0, 0, 0, 0);
const submit = () => {
  searchStore.setSearch(form);
  router.push({
    path: "/rooms",
    query: {
      checkInDate: form.checkIn,
      checkOutDate: form.checkOut,
      guestCount: form.guests,
    },
  });
};
</script>

<template>
  <form class="search-panel" @submit.prevent="submit">
    <label
      ><span>{{ t("search.checkInDate") }}</span
      ><el-date-picker
        v-model="form.checkIn"
        type="date"
        value-format="YYYY-MM-DD"
        format="YYYY / MM / DD"
        :disabled-date="disabledBeforeTomorrow"
    /></label>
    <label
      ><span>{{ t("search.checkOutDate") }}</span
      ><el-date-picker
        v-model="form.checkOut"
        type="date"
        value-format="YYYY-MM-DD"
        format="YYYY / MM / DD"
        :disabled-date="disabledBeforeTomorrow"
    /></label>
    <label
      ><span>{{ t("search.guestCount") }}</span
      ><el-select v-model="form.guests"
        ><el-option
          v-for="n in 4"
          :key="n"
          :label="t('search.guests', { count: n })"
          :value="n" /></el-select
    ></label>
    <el-button native-type="submit" type="primary" :icon="Search">{{
      t("search.submit")
    }}</el-button>
  </form>
</template>
