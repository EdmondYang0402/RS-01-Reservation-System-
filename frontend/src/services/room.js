import { apiRequest } from './request'

export const getRoomTypes = (params) => apiRequest({ url: '/room-types', method: 'get', params })
export const getRoomType = (id, params) => apiRequest({ url: `/room-types/${id}`, method: 'get', params })
export const getRoomStatus = (params) => apiRequest({ url: '/front-desk/rooms', method: 'get', params })
