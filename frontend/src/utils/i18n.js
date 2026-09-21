export const formatDate = (value, locale) => {
  if (!value) return ''
  const date = typeof value === 'string' ? new Date(`${value}T00:00:00`) : new Date(value)
  if (Number.isNaN(date.getTime())) return value
  return new Intl.DateTimeFormat(locale, {
    year: 'numeric',
    month: locale === 'en-US' ? 'short' : 'long',
    day: 'numeric',
  }).format(date)
}

export const reservationStatusKey = (status) => `reservation.status.${status}`
export const roomStatusKey = (status) => `room.status.${status}`
