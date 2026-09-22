<script setup>
import { useI18n } from "vue-i18n";
const { t } = useI18n();
</script>

<template>
  <footer class="site-footer">
    <div class="container footer-inner">
      <div>
        <strong>{{ t("brand.name") }}</strong>
        <p>{{ t("brand.tagline") }}</p>
      </div>
      <div class="footer-meta">
        <span>{{ t("brand.hours") }}</span
        ><span>© 2026 Tsukimi Stay</span>
      </div>
    </div>
  </footer>
</template>
