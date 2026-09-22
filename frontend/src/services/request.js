import axios from 'axios'
import { ElMessage } from 'element-plus'
import { mockAdapter } from '../mock/adapter'
import i18n from '../i18n'

const request = axios.create({ baseURL: import.meta.env.VITE_API_BASE_URL || '/api', timeout: 10000 })

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('rs01_token')
  const isAuthRequest = config.url?.startsWith('/auth/')
  if (token && !isAuthRequest) config.headers.Authorization = `Bearer ${token}`
  return config
})

const clearSession = () => {
  localStorage.removeItem('rs01_token')
  localStorage.removeItem('rs01_username')
  localStorage.removeItem('rs01_role')
  window.dispatchEvent(new Event('rs01:unauthorized'))
}

const showError = (error) => {
  if (error.__shown) return error
  const statusMessages = {
    401: i18n.global.t('common.unauthorized'),
    403: i18n.global.t('common.forbidden'),
  }
  const message = statusMessages[error.response?.status]
    || error.response?.data?.message
    || error.message
    || i18n.global.t('common.requestFailed')
  ElMessage.error(message)
  error.__shown = true
  return error
}

const unwrapResult = (body) => {
  if (body && Object.prototype.hasOwnProperty.call(body, 'code')) {
    if (body.code !== 200) {
      const error = new Error(body.message || i18n.global.t('common.requestFailed'))
      showError(error)
      throw error
    }
    return body.data
  }
  return body?.data ?? body
}

request.interceptors.response.use(
  (response) => unwrapResult(response.data),
  (error) => {
    const isAuthRequest = error.config?.url?.startsWith('/auth/')
    if (error.response?.status === 401 && !isAuthRequest) clearSession()
    return Promise.reject(showError(error))
  },
)

export const apiRequest = (config) => {
  if (import.meta.env.VITE_USE_MOCK === 'true') {
    return mockAdapter(config).then(unwrapResult).catch((error) => Promise.reject(showError(error)))
  }
  return request(config)
}
export default request
