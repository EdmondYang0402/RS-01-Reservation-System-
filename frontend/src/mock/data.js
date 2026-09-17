import sakuraRoom from '../assets/images/room-sakura.png'
import lavenderRoom from '../assets/images/room-lavender.png'
import heroRoom from '../assets/images/hero-hotel.png'

export const roomTypes = [
  { id: 1, name: '樱庭大床房', subtitle: 'Sakura Queen', image: sakuraRoom, capacity: 2, bedType: '1 张 1.8m 大床', price: 680, remaining: 5, size: 36, description: '朝向静谧庭院，以浅木与柔和粉色点缀，保留充足留白，让旅途在自然光里慢下来。', facilities: ['庭院景观', '浴缸', '智能马桶', '高速 Wi-Fi', '迷你吧', '早餐 2 份'] },
  { id: 2, name: '月见双床房', subtitle: 'Tsukimi Twin', image: lavenderRoom, capacity: 2, bedType: '2 张 1.2m 单人床', price: 760, remaining: 3, size: 42, description: '开阔窗景与舒适会客角相连，淡紫织物为空间增添安静层次，适合朋友或家人同行。', facilities: ['山景窗台', '独立淋浴', '智能电视', '高速 Wi-Fi', '茶具', '早餐 2 份'] },
  { id: 3, name: '和光庭院套房', subtitle: 'Wakou Suite', image: heroRoom, capacity: 3, bedType: '1 张大床 + 榻榻米', price: 1080, remaining: 2, size: 58, description: '独立起居区连接四季庭院，以克制的日式设计与现代设施提供更从容的入住体验。', facilities: ['独立起居室', '私家庭院', '浴缸', '高速 Wi-Fi', '胶囊咖啡', '早餐 3 份'] },
]

export const reservations = [
  { id: 1, reservationNo: 'RS202609120018', roomType: '樱庭大床房', checkIn: '2026-09-20', checkOut: '2026-09-22', totalAmount: 1360, status: 'CONFIRMED' },
  { id: 2, reservationNo: 'RS202608270006', roomType: '月见双床房', checkIn: '2026-08-29', checkOut: '2026-08-30', totalAmount: 760, status: 'COMPLETED' },
  { id: 3, reservationNo: 'RS202607050012', roomType: '樱庭大床房', checkIn: '2026-07-12', checkOut: '2026-07-14', totalAmount: 1360, status: 'CANCELLED' },
]

export const staffRooms = [
  { roomNumber: '301', roomType: '樱庭大床房', status: 'AVAILABLE' }, { roomNumber: '302', roomType: '樱庭大床房', status: 'OCCUPIED' },
  { roomNumber: '303', roomType: '樱庭大床房', status: 'AVAILABLE' }, { roomNumber: '305', roomType: '樱庭大床房', status: 'OUT_OF_SERVICE' },
  { roomNumber: '401', roomType: '月见双床房', status: 'OCCUPIED' }, { roomNumber: '402', roomType: '月见双床房', status: 'AVAILABLE' },
  { roomNumber: '501', roomType: '和光庭院套房', status: 'AVAILABLE' }, { roomNumber: '502', roomType: '和光庭院套房', status: 'OCCUPIED' },
]
