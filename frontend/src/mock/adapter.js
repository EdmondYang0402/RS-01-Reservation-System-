import { adminRooms, adminRoomTypes, arrivals, dailyInventory, departures, reservations, roomTypes, staffRooms } from './data'
import i18n from '../i18n'

const wait = (data) => new Promise((resolve) => setTimeout(() => resolve({ data }), 220))

export const mockAdapter = ({ url, method = 'get', data }) => {
  const requestMethod = method.toLowerCase()
  if (url === '/auth/login' && requestMethod === 'post') {
    const role = data.username === 'admin' ? 'ADMIN' : data.username === 'frontdesk' ? 'FRONT_DESK' : 'CUSTOMER'
    return wait({ token: `mock-token-${role.toLowerCase()}`, username: data.username, role })
  }
  if (url === '/auth/register' && requestMethod === 'post') return wait(1001)
  if (url === '/room-types' && requestMethod === 'get') return wait(roomTypes)
  if (url === '/reservations' && requestMethod === 'get') return wait(reservations)
  if (url === '/reservations' && requestMethod === 'post') return wait({
    reservationId: 1001,
    reservationNo: 'MOCK-RESERVATION',
    roomTypeName: roomTypes.find((room) => room.id === data.roomTypeId)?.name,
    ...data,
    totalAmount: 0,
    status: 'CONFIRMED',
  })
  if (url.match(/^\/reservations\/\d+\/cancel$/) && requestMethod === 'post') return wait(null)
  const reservationMatch = url.match(/^\/reservations\/(\d+)$/)
  if (reservationMatch && requestMethod === 'get') {
    return wait(reservations.find((item) => item.reservationId === Number(reservationMatch[1])) || reservations[0])
  }
  if (url === '/reservations/me' && requestMethod === 'get') return wait(reservations)
  if (url === '/staff/rooms' && requestMethod === 'get') return wait(staffRooms)
  if (url === '/front-desk/arrivals' && requestMethod === 'get') return wait(arrivals)
  if (url === '/front-desk/departures' && requestMethod === 'get') return wait(departures)
  if (url === '/front-desk/rooms' && requestMethod === 'get') return wait(adminRooms)
  if (url === '/front-desk/rooms/available' && requestMethod === 'get') return wait(adminRooms.filter((room) => room.status === 'AVAILABLE'))
  if (url.match(/^\/front-desk\/reservations\/\d+\/(check-in|check-out|no-show)$/) && requestMethod === 'post') return wait(null)
  if (url === '/admin/room-types' && requestMethod === 'get') return wait(adminRoomTypes)
  if (url === '/admin/rooms' && requestMethod === 'get') return wait(adminRooms)
  if (url === '/admin/inventory' && requestMethod === 'get') return wait(dailyInventory)
  if (url === '/admin/inventory/initialize' && requestMethod === 'post') return wait(3)
  if ((url === '/admin/room-types' || url === '/admin/rooms') && requestMethod === 'post') return wait(data)
  if (url.match(/^\/admin\/(room-types|rooms)\/\d+$/) && requestMethod === 'put') return wait(null)
  if (url === '/availability' && requestMethod === 'get') return wait(roomTypes.map((room) => ({
    roomTypeId: room.id,
    name: room.name,
    capacity: room.capacity,
    bedType: room.bedType,
    basePrice: room.price,
    checkInDate: room.checkInDate || '2026-09-22',
    checkOutDate: room.checkOutDate || '2026-09-23',
    nightCount: 1,
    availableRooms: room.remaining,
    totalAmount: room.price,
    image: room.image,
    subtitle: room.subtitle,
    description: room.description,
    facilities: room.facilities,
    size: room.size,
  })))
  const match = url.match(/^\/room-types\/(\d+)$/)
  if (match) {
    const room = roomTypes.find((item) => item.id === Number(match[1]))
    return room && requestMethod === 'get' ? wait(room) : Promise.reject(new Error(i18n.global.t('room.unavailable')))
  }
  return Promise.reject(new Error(`${i18n.global.t('common.requestFailed')}: ${url}`))
}
