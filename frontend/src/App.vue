<script setup>
import { computed, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import ja from 'element-plus/es/locale/lang/ja'
import en from 'element-plus/es/locale/lang/en'

const route = useRoute()
const { locale, t } = useI18n()
const elementLocales = { 'zh-CN': zhCn, 'ja-JP': ja, 'en-US': en }
const elementLocale = computed(() => elementLocales[locale.value] || zhCn)

watch([locale, () => route.meta.titleKey], () => {
  document.title = `${t(route.meta.titleKey || 'route.home')} · Tsukimi Stay`
}, { immediate: true })
</script>

<template><el-config-provider :locale="elementLocale"><RouterView /></el-config-provider></template>
