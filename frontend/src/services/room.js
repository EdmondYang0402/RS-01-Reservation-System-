import { apiRequest } from './request'
import { getAvailability } from './inventory'

export const getRoomTypes = (params) => getAvailability(params)
export const getRoomType = async (id, params) => {
  const roomTypes = await getAvailability(params)
  return roomTypes.find((roomType) => roomType.roomTypeId === Number(id)) || null
}
export const getRoomStatus = (params) => apiRequest({ url: '/front-desk/rooms', method: 'get', params })
