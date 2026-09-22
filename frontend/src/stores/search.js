import { defineStore } from 'pinia'

const addDays = (days) => {
  const date = new Date()
  date.setDate(date.getDate() + days)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

export const useSearchStore = defineStore('search', {
  state: () => ({ checkIn: addDays(1), checkOut: addDays(2), guests: 2 }),
  actions: { setSearch(payload) { Object.assign(this, payload) } },
})
