<script setup>
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '../stores/auth'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()
const { t } = useI18n()
const loading = ref(false)
const form = reactive({ username: '', password: '' })

const submit = async () => {
  loading.value = true
  try {
    const session = await auth.login(form)
    ElMessage.success(t('auth.loginSuccess'))
    const home = { CUSTOMER: '/rooms', FRONT_DESK: '/front-desk/arrivals', ADMIN: '/admin' }
    router.push(route.query.redirect || home[session.role] || '/')
  } finally { loading.value = false }
}
</script>

<template>
  <section class="auth-page"><el-card class="auth-card" shadow="never"><span class="eyebrow">{{ t('auth.welcome') }}</span><h1>{{ t('auth.loginTitle') }}</h1><p>{{ t('auth.mockHint') }}</p><el-form label-position="top" @submit.prevent="submit"><el-form-item :label="t('auth.username')"><el-input v-model="form.username" autocomplete="username" /></el-form-item><el-form-item :label="t('auth.password')"><el-input v-model="form.password" type="password" show-password autocomplete="current-password" /></el-form-item><el-button type="primary" native-type="submit" :loading="loading">{{ t('auth.login') }}</el-button></el-form><RouterLink to="/register">{{ t('auth.noAccount') }}</RouterLink></el-card></section>
</template>
