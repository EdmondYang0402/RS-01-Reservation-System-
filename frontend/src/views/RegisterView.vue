<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const auth = useAuthStore()
const { t } = useI18n()
const loading = ref(false)
const form = reactive({ username: '', password: '', name: '', email: '', phone: '' })

const submit = async () => {
  loading.value = true
  try {
    await auth.register(form)
    ElMessage.success(t('auth.registerSuccess'))
    router.push('/login')
  } finally { loading.value = false }
}
</script>

<template>
  <section class="auth-page"><el-card class="auth-card wide" shadow="never"><span class="eyebrow">{{ t('auth.createAccount') }}</span><h1>{{ t('auth.registerTitle') }}</h1><el-form label-position="top" @submit.prevent="submit"><div class="form-grid"><el-form-item :label="t('auth.username')"><el-input v-model="form.username" /></el-form-item><el-form-item :label="t('auth.fullName')"><el-input v-model="form.name" /></el-form-item><el-form-item :label="t('auth.password')"><el-input v-model="form.password" type="password" show-password /></el-form-item><el-form-item :label="t('auth.email')"><el-input v-model="form.email" type="email" /></el-form-item><el-form-item :label="t('auth.phone')"><el-input v-model="form.phone" /></el-form-item></div><el-button type="primary" native-type="submit" :loading="loading">{{ t('auth.register') }}</el-button></el-form><RouterLink to="/login">{{ t('auth.hasAccount') }}</RouterLink></el-card></section>
</template>
