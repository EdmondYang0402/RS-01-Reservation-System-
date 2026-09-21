import { createRouter, createWebHistory } from 'vue-router'
import AppLayout from '../layout/AppLayout.vue'
import FrontDeskLayout from '../layout/FrontDeskLayout.vue'
import AdminLayout from '../layout/AdminLayout.vue'
import { i18n } from '../i18n'

const roleHome = { CUSTOMER: '/rooms', FRONT_DESK: '/front-desk/arrivals', ADMIN: '/admin' }

const router = createRouter({
  history: createWebHistory(),
  scrollBehavior: () => ({ top: 0 }),
  routes: [{
    path: '/', component: AppLayout, children: [
      { path: '', name: 'home', component: () => import('../views/HomeView.vue'), meta: { titleKey: 'nav.home' } },
      { path: 'rooms', name: 'rooms', component: () => import('../views/RoomTypeListView.vue'), meta: { titleKey: 'route.rooms' } },
      { path: 'rooms/:id', name: 'room-detail', component: () => import('../views/RoomTypeDetailView.vue'), meta: { titleKey: 'route.roomDetail' } },
      { path: 'reservations', name: 'reservations', component: () => import('../views/MyReservationsView.vue'), meta: { titleKey: 'nav.reservations', roles: ['CUSTOMER'] } },
      { path: 'reservations/:id', name: 'reservation-detail', component: () => import('../views/ReservationDetailView.vue'), meta: { titleKey: 'route.reservationDetail', roles: ['CUSTOMER'] } },
      { path: 'login', name: 'login', component: () => import('../views/LoginView.vue'), meta: { titleKey: 'nav.login' } },
      { path: 'register', name: 'register', component: () => import('../views/RegisterView.vue'), meta: { titleKey: 'auth.registerTitle' } },
      { path: 'front-desk', component: FrontDeskLayout, meta: { roles: ['FRONT_DESK', 'ADMIN'] }, children: [
        { path: '', redirect: { name: 'front-desk-arrivals' } },
        { path: 'arrivals', name: 'front-desk-arrivals', component: () => import('../views/TodayArrivalsView.vue'), meta: { titleKey: 'nav.arrivals', roles: ['FRONT_DESK', 'ADMIN'] } },
        { path: 'departures', name: 'front-desk-departures', component: () => import('../views/TodayDeparturesView.vue'), meta: { titleKey: 'nav.departures', roles: ['FRONT_DESK', 'ADMIN'] } },
        { path: 'rooms', name: 'front-desk-rooms', component: () => import('../views/StaffRoomStatusView.vue'), meta: { titleKey: 'nav.roomStatus', roles: ['FRONT_DESK', 'ADMIN'] } },
      ] },
      { path: 'admin', component: AdminLayout, meta: { roles: ['ADMIN'] }, children: [
        { path: '', name: 'admin-dashboard', component: () => import('../views/AdminDashboardView.vue'), meta: { titleKey: 'nav.dashboard', roles: ['ADMIN'] } },
        { path: 'hotels', name: 'admin-hotels', component: () => import('../views/AdminHotelView.vue'), meta: { titleKey: 'nav.hotel', roles: ['ADMIN'] } },
        { path: 'room-types', name: 'admin-room-types', component: () => import('../views/AdminRoomTypeView.vue'), meta: { titleKey: 'nav.roomTypes', roles: ['ADMIN'] } },
        { path: 'rooms', name: 'admin-rooms', component: () => import('../views/AdminRoomView.vue'), meta: { titleKey: 'nav.rooms', roles: ['ADMIN'] } },
        { path: 'inventory', name: 'admin-inventory', component: () => import('../views/AdminInventoryView.vue'), meta: { titleKey: 'nav.inventory', roles: ['ADMIN'] } },
      ] },
    ],
  }],
})

router.beforeEach((to) => {
  const roles = to.matched.flatMap((record) => record.meta.roles || [])
  if (!roles.length) return true
  const token = localStorage.getItem('rs01_token')
  const role = localStorage.getItem('rs01_role')
  if (!token) return { name: 'login', query: { redirect: to.fullPath } }
  if (!roles.includes(role)) return roleHome[role] || '/'
  return true
})

router.afterEach((to) => {
  document.title = `${i18n.global.t(to.meta.titleKey || 'brand.name')} · ${i18n.global.t('brand.name')}`
})

export default router
