import { apiRequest } from './request'
import sakuraRoom from '../assets/images/room-sakura.png'
import lavenderRoom from '../assets/images/room-lavender.png'
import heroRoom from '../assets/images/hero-hotel.png'

const roomImages = [sakuraRoom, lavenderRoom, heroRoom]

export const mapAvailableRoomType = (room, index = 0) => ({
  ...room,
  id: room.roomTypeId,
  price: room.basePrice,
  remaining: room.availableRooms,
  image: room.image || roomImages[index % roomImages.length],
  subtitle: room.subtitle,
  facilities: room.facilities || [],
})

export const getAvailability = async (params) => {
  const rooms = await apiRequest({ url: '/availability', method: 'get', params })
  return rooms.map(mapAvailableRoomType)
}
export const getAdminInventory = (params) => apiRequest({ url: '/admin/inventory', method: 'get', params })
export const initializeInventory = (data) => apiRequest({ url: '/admin/inventory/initialize', method: 'post', data })
