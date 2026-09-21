export default {
  language: { label: '言語', zhCN: '简体中文', jaJP: '日本語', enUS: 'English' },
  brand: { name: '月見ホテル', tagline: '光と庭に包まれて、心安らぐひとときを。', hours: 'チェックイン 15:00 · チェックアウト 11:00' },
  nav: { toggle: 'ナビゲーションを切り替える', home: 'ホーム', rooms: '客室', availability: '空室検索', reservations: '予約一覧', arrivals: '本日のチェックイン', departures: '本日のチェックアウト', roomStatus: '客室状況', dashboard: '概要', hotel: 'ホテル', roomTypes: '客室タイプ', inventory: '在庫', roomTypesAdmin: '客室タイプ管理', roomsAdmin: '客室管理', inventoryAdmin: '在庫管理', login: 'ログイン', logout: 'ログアウト' },
  common: { id: 'ID', name: '名称', description: '説明', status: 'ステータス', actions: '操作', search: '検索', refresh: '更新', add: '追加', edit: '編集', save: '保存', submit: '送信', cancel: 'キャンセル', confirm: '確認', details: '詳細を見る', date: '日付', price: '料金', requestSubmitted: 'リクエストを送信しました', requestFailed: 'リクエストに失敗しました', unassigned: '未割り当て', all: 'すべて' },
  home: {
    heroAlt: '月見ホテルの中庭に面した客室', heroEyebrow: '街の中の静かな滞在', heroTitle: 'やさしい月明かりと\n過ごす一夜', heroDescription: '和の余白と現代の快適さが調和する、旅の静かな居場所です。',
    roomsEyebrow: '月見でのご滞在', roomsTitle: '旅のかたちに寄り添う客室', roomsDescription: '自然光、やわらかなファブリック、静かな庭が心地よい休息をつくります。', viewAllRooms: 'すべての客室を見る →',
    promises: [{ title: '静かな立地', description: '喧騒を離れながら、街の利便性も身近に。' }, { title: '上質な眠り', description: '肌触りのよい寝具とやさしい照明で快適な夜を。' }, { title: '心温まる朝食', description: '旬の食材で、ゆったりとした朝を始めます。' }],
  },
  search: { checkInDate: 'チェックイン日', checkOutDate: 'チェックアウト日', guestCount: '宿泊人数', guests: '{count}名', submit: '空室を検索' },
  room: {
    room: '客室', roomType: '客室タイプ', roomTypeId: '客室タイプ ID', roomNumber: '客室番号', floor: '階', bedType: 'ベッドタイプ', capacity: '定員', basePrice: '基本料金', totalRooms: '客室数', hotelId: 'ホテル ID',
    listEyebrow: '客室のご案内', listTitle: '客室一覧', listDescription: '旅に合う空間を選び、やわらかな朝の光に目覚めてください。', empty: '選択した日程で予約可能な客室はありません', availableStay: '心地よい滞在', remaining: '残り{count}室', guestUnit: '{count}名', perNight: '1泊', from: '〜',
    intro: '客室のご案内', defaultDescription: '快適な客室です。設備の詳細はホテルでの提供内容をご確認ください。', facilities: '客室設備', nights: '{count}泊', nightStay: '{count}泊', taxIncluded: '税込', backendPricing: 'サーバー算出', total: '合計', availableCount: 'この日程の空室数：{count}室', book: 'この客室を予約', unavailable: '選択した日程ではこの客室タイプを予約できません',
    bookingTitle: '宿泊者情報の入力', guestName: '宿泊者名', phone: '電話番号', confirmBooking: '予約を確定', bookingCreated: '予約が完了しました',
    status: { AVAILABLE: '空室', OCCUPIED: '利用中', OUT_OF_SERVICE: '利用停止' },
  },
  reservation: {
    reservation: '予約', my: '予約一覧', myDescription: '今後の宿泊予定と過去のご利用を確認できます。', reservationNo: '予約番号', number: '予約番号', guest: '宿泊者', checkIn: 'チェックイン', checkOut: 'チェックアウト', checkInDate: 'チェックイン日', checkOutDate: 'チェックアウト日', totalAmount: '合計金額', cancel: '予約をキャンセル', cancelTitle: '予約のキャンセル', cancelConfirm: '予約 {no} をキャンセルしますか？', cancelled: '予約をキャンセルしました', detail: '予約詳細', phone: '電話番号', assignedRoom: '客室番号', nightlyDetails: '宿泊日ごとの明細',
    status: { CONFIRMED: '予約確定', CHECKED_IN: 'チェックイン済み', CHECKED_OUT: 'チェックアウト済み', CANCELLED: 'キャンセル済み', NO_SHOW: '無断不泊', COMPLETED: '完了' },
  },
  auth: { welcome: 'おかえりなさい', loginTitle: '月見にログイン', mockHint: 'Mock モードでは admin、frontdesk、または任意の顧客名を使用できます。', username: 'ユーザー名', password: 'パスワード', login: 'ログイン', loginSuccess: 'ログインしました', noAccount: 'アカウントをお持ちでない方はこちら', createAccount: 'アカウント作成', registerTitle: '新規登録', fullName: '氏名', email: 'メールアドレス', phone: '電話番号', register: '登録', registerSuccess: '登録が完了しました。ログインしてください', hasAccount: 'アカウントをお持ちの方はこちら' },
  admin: {
    title: '管理', dashboard: '運営概要', hotelManagement: 'ホテル情報', hotelCount: 'ホテル', roomTypeCount: '客室タイプ', roomCount: '客室', availableRoomCount: '利用可能', address: '住所', phone: '電話', checkInTime: 'チェックイン時刻', checkOutTime: 'チェックアウト時刻', roomTypeManagement: '客室タイプ管理', addRoomType: '客室タイプを追加', editRoomType: '客室タイプを編集', roomManagement: '客室管理', addRoom: '客室を追加', editRoom: '客室を編集', inventory: '日別在庫', initializeInventory: '在庫を初期化', startDate: '開始日', endDate: '終了日', totalInventory: '総在庫', reservedCount: '予約済み', outOfServiceCount: '利用停止', initializeTitle: '日別在庫の初期化', initializeNotice: 'バックエンドの初期化 API のみを呼び出し、フロントエンドでは在庫を計算・変更しません。', initializeSuccess: '初期化が完了し、{count}件の在庫を追加しました',
  },
  frontDesk: { title: 'フロント', arrivals: '本日のチェックイン予定', arrivalsZh: '本日のチェックイン予定', departures: '本日のチェックアウト予定', roomStatusTitle: '本日の客室状況', roomStatusDescription: 'フロント向け客室状況', roomLabel: '客室', checkIn: 'チェックイン', checkOut: 'チェックアウト', noShow: '無断不泊', noShowConfirm: '{no} を無断不泊として処理しますか？', checkOutConfirm: '{no} のチェックアウトを行いますか？', assignRoomId: '割り当て客室 ID', submitCheckIn: 'チェックインを確定', checkInSubmitted: 'チェックインのリクエストを送信しました', assignedRoom: '割当客室', availableRooms: '空室', noAvailableRoom: '空室がありません' },
  route: { home: '月見ホテル', rooms: '客室一覧', roomDetail: '客室詳細', reservations: '予約一覧', reservationDetail: '予約詳細', staffRooms: '客室状況', login: 'ログイン', register: '新規登録', arrivals: '本日のチェックイン', departures: '本日のチェックアウト', adminRoomTypes: '客室タイプ管理', adminRooms: '客室管理', adminInventory: '在庫管理' },
}
