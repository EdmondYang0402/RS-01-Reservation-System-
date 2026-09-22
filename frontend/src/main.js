import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './styles.css'
import App from './App.vue'
import router from './router'
import i18n from './i18n'

createApp(App).use(createPinia()).use(i18n).use(router).use(ElementPlus).mount('#app')

window.addEventListener('rs01:unauthorized', () => {
  if (router.currentRoute.value.name !== 'login') {
    router.push({ name: 'login', query: { redirect: router.currentRoute.value.fullPath } })
  }
})
