import { createRouter, createWebHistory } from 'vue-router'
import AppLayout from '../layout/AppLayout.vue'

const router = createRouter({
  history: createWebHistory(),
  scrollBehavior: () => ({ top: 0 }),
  routes: [{
    path: '/', component: AppLayout, children: [
      { path: '', name: 'home', component: () => import('../views/HomeView.vue'), meta: { title: '栖月酒店' } },
      { path: 'rooms', name: 'rooms', component: () => import('../views/RoomTypeListView.vue'), meta: { title: '房型一览' } },
      { path: 'rooms/:id', name: 'room-detail', component: () => import('../views/RoomTypeDetailView.vue'), meta: { title: '房型详情' } },
      { path: 'reservations', name: 'reservations', component: () => import('../views/MyReservationsView.vue'), meta: { title: '我的预订' } },
      { path: 'staff/rooms', name: 'staff-rooms', component: () => import('../views/StaffRoomStatusView.vue'), meta: { title: '房态管理' } },
    ],
  }],
})

router.afterEach((to) => { document.title = `${to.meta.title || '栖月酒店'} · Tsukimi Stay` })
export default router
