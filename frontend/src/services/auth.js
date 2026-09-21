import { apiRequest } from './request'

export const login = (credentials) => apiRequest({ url: '/auth/login', method: 'post', data: credentials })
export const register = (profile) => apiRequest({ url: '/auth/register', method: 'post', data: profile })
