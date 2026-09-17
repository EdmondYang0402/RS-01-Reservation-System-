import { defineStore } from 'pinia'

const addDays = (days) => {
  const date = new Date()
  date.setDate(date.getDate() + days)
  return date.toISOString().slice(0, 10)
}

export const useSearchStore = defineStore('search', {
  state: () => ({ checkIn: addDays(1), checkOut: addDays(2), guests: 2 }),
  actions: { setSearch(payload) { Object.assign(this, payload) } },
})
