export default {
  language: { label: '语言', zhCN: '简体中文', jaJP: '日本語', enUS: 'English' },
  brand: { name: '栖月酒店', tagline: '在光与庭院之间，安心住一晚。', hours: '入住 15:00 · 退房 11:00' },
  nav: { toggle: '切换导航', home: '首页', rooms: '客房', availability: '查房', reservations: '我的预订', arrivals: '今日抵店', departures: '今日离店', roomStatus: '房态', dashboard: '概览', hotel: '酒店', roomTypes: '房型', inventory: '库存', roomTypesAdmin: '房型管理', roomsAdmin: '房间管理', inventoryAdmin: '库存查询', login: '登录', logout: '退出' },
  common: { id: 'ID', name: '名称', description: '描述', status: '状态', actions: '操作', search: '查询', refresh: '刷新', add: '新增', edit: '编辑', save: '保存', submit: '提交', cancel: '取消', confirm: '确认', details: '查看详情', date: '日期', price: '价格', requestSubmitted: '请求已提交', requestFailed: '请求失败', unassigned: '未分配', all: '全部' },
  home: {
    heroAlt: '栖月酒店庭院客房', heroEyebrow: '城市中的静谧居所', heroTitle: '留一晚给\n温柔的月色', heroDescription: '日式留白与现代舒适相遇，回到旅途里安静的一隅。',
    roomsEyebrow: '入住栖月', roomsTitle: '为每一种旅途，留一间好房', roomsDescription: '自然光、柔软织物与安静庭院，构成恰到好处的休息空间。', viewAllRooms: '查看全部房型 →',
    promises: [{ title: '静谧选址', description: '远离喧闹，也不离城市便利。' }, { title: '细致睡眠', description: '亲肤寝具与柔和照明伴你入眠。' }, { title: '温暖早餐', description: '用当季食材开启从容早晨。' }],
  },
  search: { checkInDate: '入住日期', checkOutDate: '退房日期', guestCount: '入住人数', guests: '{count} 位客人', submit: '查询客房' },
  room: {
    room: '客房', roomType: '房型', roomTypeId: '房型 ID', roomNumber: '房号', floor: '楼层', bedType: '床型', capacity: '容纳人数', basePrice: '基础价格', totalRooms: '房间数', hotelId: '酒店 ID',
    listEyebrow: '客房一览', listTitle: '客房一览', listDescription: '选择适合你的空间，在柔和晨光里醒来。', empty: '所选日期暂无可订房型', availableStay: '舒适住宿', remaining: '仅余 {count} 间', guestUnit: '{count} 人', perNight: '每晚', from: '起',
    intro: '房间介绍', defaultDescription: '舒适客房，具体设施请以酒店实际提供为准。', facilities: '客房设施', nights: '{count} 晚', nightStay: '{count} 晚住宿', taxIncluded: '含税', backendPricing: '后端计价', total: '合计', availableCount: '当前日期可订 {count} 间', book: '预订此房型', unavailable: '该房型在所选日期不可订',
    bookingTitle: '填写入住信息', guestName: '住客姓名', phone: '联系电话', confirmBooking: '确认预订', bookingCreated: '预订创建成功',
    status: { AVAILABLE: '可用', OCCUPIED: '已入住', OUT_OF_SERVICE: '停用' },
  },
  reservation: {
    reservation: '预订', my: '我的预订', myDescription: '查看你的入住计划与历史旅程。', reservationNo: '预订编号', number: '预订号', guest: '住客', checkIn: '入住', checkOut: '退房', checkInDate: '入住日期', checkOutDate: '退房日期', totalAmount: '订单总额', cancel: '取消预订', cancelTitle: '取消预订', cancelConfirm: '确认取消预订 {no}？', cancelled: '预订已取消', detail: '预订详情', phone: '联系电话', assignedRoom: '房号', nightlyDetails: '每晚明细',
    status: { CONFIRMED: '已确认', CHECKED_IN: '已入住', CHECKED_OUT: '已退房', CANCELLED: '已取消', NO_SHOW: '未到店', COMPLETED: '已完成' },
  },
  auth: { welcome: '欢迎回来', loginTitle: '登录栖月', mockHint: 'Mock 模式可使用 admin、frontdesk 或任意客户用户名。', username: '用户名', password: '密码', login: '登录', loginSuccess: '登录成功', noAccount: '还没有账号？注册', createAccount: '创建账号', registerTitle: '注册账号', fullName: '姓名', email: '邮箱', phone: '手机号', register: '注册', registerSuccess: '注册成功，请登录', hasAccount: '已有账号？登录' },
  admin: {
    title: '后台管理', dashboard: '运营概览', hotelManagement: '酒店资料', hotelCount: '酒店', roomTypeCount: '房型', roomCount: '房间', availableRoomCount: '可用房', address: '地址', phone: '电话', checkInTime: '入住时间', checkOutTime: '退房时间', roomTypeManagement: '房型管理', addRoomType: '新增房型', editRoomType: '编辑房型', roomManagement: '房间管理', addRoom: '新增房间', editRoom: '编辑房间', inventory: '每日库存', initializeInventory: '初始化库存', startDate: '开始日期', endDate: '结束日期', totalInventory: '总库存', reservedCount: '已预订', outOfServiceCount: '停用', initializeTitle: '初始化每日库存', initializeNotice: '仅调用后端初始化接口，不在前端计算或修改库存。', initializeSuccess: '初始化完成，新增 {count} 条库存记录',
  },
  frontDesk: { title: '前台', arrivals: '今日抵店', arrivalsZh: '今日抵店', departures: '今日离店', roomStatusTitle: '今日房态', roomStatusDescription: '前台房间状态概览', roomLabel: '客房', checkIn: '办理入住', checkOut: '办理退房', noShow: '未到店', noShowConfirm: '确认将 {no} 标记为未到店？', checkOutConfirm: '确认为 {no} 办理退房？', assignRoomId: '分配房间 ID', submitCheckIn: '提交入住', checkInSubmitted: '入住请求已提交', assignedRoom: '已分配房间', availableRooms: '可用房间', noAvailableRoom: '暂无可用房间' },
  route: { home: '栖月酒店', rooms: '房型一览', roomDetail: '房型详情', reservations: '我的预订', reservationDetail: '预订详情', staffRooms: '房态管理', login: '登录', register: '注册', arrivals: '今日抵店', departures: '今日离店', adminRoomTypes: '房型管理', adminRooms: '房间管理', adminInventory: '库存查询' },
}
