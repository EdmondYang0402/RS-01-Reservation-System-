import { reservations, roomTypes, staffRooms } from './data'

const wait = (data) => new Promise((resolve) => setTimeout(() => resolve({ data }), 220))

export const mockAdapter = ({ url, method = 'get' }) => {
  if (method.toLowerCase() !== 'get') return Promise.reject(new Error('Mock 暂不支持该请求'))
  if (url === '/room-types') return wait(roomTypes)
  if (url === '/reservations/me') return wait(reservations)
  if (url === '/staff/rooms') return wait(staffRooms)
  const match = url.match(/^\/room-types\/(\d+)$/)
  if (match) {
    const room = roomTypes.find((item) => item.id === Number(match[1]))
    return room ? wait(room) : Promise.reject(new Error('房型不存在'))
  }
  return Promise.reject(new Error(`未匹配的 Mock 请求：${url}`))
}
