import { apiRequest } from './request'

export const getRoomTypes = (params) => apiRequest({ url: '/room-types', method: 'get', params })
export const getRoomTypeDetail = (id, params) => apiRequest({ url: `/room-types/${id}`, method: 'get', params })
export const getMyReservations = () => apiRequest({ url: '/reservations/me', method: 'get' })
export const getStaffRoomStatus = () => apiRequest({ url: '/staff/rooms', method: 'get' })
