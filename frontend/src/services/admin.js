import { apiRequest } from './request'

export const getHotels = (params) => apiRequest({ url: '/admin/hotels', method: 'get', params })
export const createHotel = (data) => apiRequest({ url: '/admin/hotels', method: 'post', data })
export const updateHotel = (id, data) => apiRequest({ url: `/admin/hotels/${id}`, method: 'put', data })
export const deleteHotel = (id) => apiRequest({ url: `/admin/hotels/${id}`, method: 'delete' })
export const getAdminRoomTypes = (params) => apiRequest({ url: '/admin/room-types', method: 'get', params })
export const createRoomType = (data) => apiRequest({ url: '/admin/room-types', method: 'post', data })
export const updateRoomType = (id, data) => apiRequest({ url: `/admin/room-types/${id}`, method: 'put', data })
export const deleteRoomType = (id) => apiRequest({ url: `/admin/room-types/${id}`, method: 'delete' })
export const getAdminRooms = (params) => apiRequest({ url: '/admin/rooms', method: 'get', params })
export const createRoom = (data) => apiRequest({ url: '/admin/rooms', method: 'post', data })
export const updateRoom = (id, data) => apiRequest({ url: `/admin/rooms/${id}`, method: 'put', data })
export const deleteRoom = (id) => apiRequest({ url: `/admin/rooms/${id}`, method: 'delete' })
