import { apiRequest } from './request'

export const getReservations = () => apiRequest({ url: '/reservations', method: 'get' })
export const getReservation = (id) => apiRequest({ url: `/reservations/${id}`, method: 'get' })
export const createReservation = (data) => apiRequest({ url: '/reservations', method: 'post', data })
export const cancelReservation = (id) => apiRequest({ url: `/reservations/${id}/cancel`, method: 'post' })
export const getTodayArrivals = () => apiRequest({ url: '/front-desk/arrivals', method: 'get' })
export const getTodayDepartures = () => apiRequest({ url: '/front-desk/departures', method: 'get' })
export const getFrontDeskRooms = (params) => apiRequest({ url: '/front-desk/rooms', method: 'get', params })
export const getAvailableRooms = (roomTypeId) => apiRequest({ url: '/front-desk/rooms/available', method: 'get', params: { roomTypeId } })
export const checkInReservation = (id, roomId) => apiRequest({ url: `/front-desk/reservations/${id}/check-in`, method: 'post', data: { roomId } })
export const checkOutReservation = (id) => apiRequest({ url: `/front-desk/reservations/${id}/check-out`, method: 'post' })
export const markReservationNoShow = (id) => apiRequest({ url: `/front-desk/reservations/${id}/no-show`, method: 'post' })
