<script setup>
import { useI18n } from "vue-i18n";
const { t } = useI18n();
const links = [
  ["front-desk-arrivals", "nav.arrivals"],
  ["front-desk-departures", "nav.departures"],
  ["front-desk-rooms", "nav.roomStatus"],
];
</script>

<template>
  <div class="role-layout">
    <aside class="role-sidebar">
      <strong>{{ t("frontDesk.title") }}</strong>
      <RouterLink
        v-for="link in links"
        :key="link[0]"
        :to="{ name: link[0] }"
        >{{ t(link[1]) }}</RouterLink
      >
    </aside>
    <div class="role-content"><RouterView /></div>
  </div>
</template>
