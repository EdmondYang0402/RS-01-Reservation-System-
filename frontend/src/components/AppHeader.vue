<script setup>
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import { Menu, Close } from "@element-plus/icons-vue";
import { useAuthStore } from "../stores/auth";
import LanguageSelector from "./LanguageSelector.vue";

const menuOpen = ref(false);
const router = useRouter();
const auth = useAuthStore();
const { t } = useI18n();
const links = [
  { to: "/", key: "nav.home", roles: [] },
  { to: "/rooms", key: "nav.availability", roles: ["CUSTOMER"] },
  { to: "/reservations", key: "nav.reservations", roles: ["CUSTOMER"] },
  { to: "/front-desk/arrivals", key: "nav.arrivals", roles: ["FRONT_DESK"] },
  {
    to: "/front-desk/departures",
    key: "nav.departures",
    roles: ["FRONT_DESK"],
  },
  { to: "/front-desk/rooms", key: "nav.roomStatus", roles: ["FRONT_DESK"] },
  { to: "/admin", key: "nav.dashboard", roles: ["ADMIN"] },
  { to: "/admin/hotels", key: "nav.hotel", roles: ["ADMIN"] },
  { to: "/admin/room-types", key: "nav.roomTypes", roles: ["ADMIN"] },
  { to: "/admin/rooms", key: "nav.rooms", roles: ["ADMIN"] },
  { to: "/admin/inventory", key: "nav.inventory", roles: ["ADMIN"] },
];
const visibleLinks = computed(() =>
  links.filter(
    (link) =>
      !link.roles.length ||
      link.roles.includes(auth.role) ||
      (!auth.role && link.to === "/rooms"),
  ),
);
const logout = () => {
  auth.logout();
  menuOpen.value = false;
  router.push("/");
};
</script>

<template>
  <header class="site-header">
    <div class="header-inner container">
      <RouterLink class="brand" to="/" @click="menuOpen = false">
        <span class="brand-mark">月</span
        ><span
          ><strong>{{ t("brand.name") }}</strong
          ><small>TSUKIMI STAY</small></span
        >
      </RouterLink>
      <button
        class="nav-toggle"
        :aria-label="t('nav.toggle')"
        @click="menuOpen = !menuOpen"
      >
        <el-icon><Close v-if="menuOpen" /><Menu v-else /></el-icon>
      </button>
      <nav :class="['main-nav', { open: menuOpen }]">
        <RouterLink
          v-for="link in visibleLinks"
          :key="link.to"
          :to="link.to"
          @click="menuOpen = false"
          >{{ t(link.key) }}</RouterLink
        >
        <LanguageSelector />
        <template v-if="auth.isAuthenticated"
          ><span class="nav-user">{{ auth.username }}</span
          ><button class="nav-action" @click="logout">
            {{ t("nav.logout") }}
          </button></template
        >
        <RouterLink v-else to="/login" @click="menuOpen = false">{{
          t("nav.login")
        }}</RouterLink>
      </nav>
    </div>
  </header>
</template>
