<script setup>
import { useI18n } from "vue-i18n";
const { t } = useI18n();
const links = [
  ["admin-dashboard", "nav.dashboard"],
  ["admin-hotels", "nav.hotel"],
  ["admin-room-types", "nav.roomTypes"],
  ["admin-rooms", "nav.rooms"],
  ["admin-inventory", "nav.inventory"],
];
</script>

<template>
  <div class="role-layout">
    <aside class="role-sidebar">
      <strong>{{ t("admin.title") }}</strong>
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
