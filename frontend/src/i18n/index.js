import { createI18n } from 'vue-i18n'
import zhCN from './locales/zh-CN'
import jaJP from './locales/ja-JP'
import enUS from './locales/en-US'

export const LOCALE_STORAGE_KEY = 'rs01_locale'
export const SUPPORTED_LOCALES = ['zh-CN', 'ja-JP', 'en-US']

const browserLocale = () => {
  const language = navigator.language || ''
  if (language.toLowerCase().startsWith('zh')) return 'zh-CN'
  if (language.toLowerCase().startsWith('ja')) return 'ja-JP'
  return 'en-US'
}

const savedLocale = localStorage.getItem(LOCALE_STORAGE_KEY)
const initialLocale = SUPPORTED_LOCALES.includes(savedLocale) ? savedLocale : browserLocale()

const i18n = createI18n({
  legacy: false,
  locale: initialLocale,
  fallbackLocale: 'zh-CN',
  messages: { 'zh-CN': zhCN, 'ja-JP': jaJP, 'en-US': enUS },
})

export { i18n }
export const supportedLocales = SUPPORTED_LOCALES

export const setLocale = (locale) => {
  if (!SUPPORTED_LOCALES.includes(locale)) return
  i18n.global.locale.value = locale
  localStorage.setItem(LOCALE_STORAGE_KEY, locale)
  document.documentElement.lang = locale
}

document.documentElement.lang = initialLocale

export default i18n
