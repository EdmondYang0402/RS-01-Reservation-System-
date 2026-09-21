import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import * as authApi from '../services/auth'

const TOKEN_KEY = 'rs01_token'
const USERNAME_KEY = 'rs01_username'
const ROLE_KEY = 'rs01_role'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem(TOKEN_KEY) || '')
  const username = ref(localStorage.getItem(USERNAME_KEY) || '')
  const role = ref(localStorage.getItem(ROLE_KEY) || '')
  const isAuthenticated = computed(() => Boolean(token.value))

  const saveSession = (session) => {
    token.value = session.token || ''
    username.value = session.username || ''
    role.value = session.role || ''
    localStorage.setItem(TOKEN_KEY, token.value)
    localStorage.setItem(USERNAME_KEY, username.value)
    localStorage.setItem(ROLE_KEY, role.value)
  }

  const login = async (credentials) => {
    const session = await authApi.login(credentials)
    saveSession(session)
    return session
  }
  const register = async (profile) => authApi.register(profile)
  const logout = () => {
    token.value = ''; username.value = ''; role.value = ''
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(USERNAME_KEY)
    localStorage.removeItem(ROLE_KEY)
  }

  window.addEventListener('rs01:unauthorized', logout)

  return { token, username, role, isAuthenticated, login, register, logout }
})
