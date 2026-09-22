<script setup>
import { computed } from "vue";
import { useI18n } from "vue-i18n";
import { setLocale } from "../i18n";

const { locale, t } = useI18n();
const selected = computed({ get: () => locale.value, set: setLocale });
const options = [
  { value: "zh-CN", key: "language.zhCN" },
  { value: "ja-JP", key: "language.jaJP" },
  { value: "en-US", key: "language.enUS" },
];
</script>

<template>
  <el-select
    v-model="selected"
    class="language-selector"
    :aria-label="t('language.label')"
  >
    <el-option
      v-for="option in options"
      :key="option.value"
      :label="t(option.key)"
      :value="option.value"
    />
  </el-select>
</template>
