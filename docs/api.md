# API

All application responses use `Result<T>`. Send authenticated requests with `Authorization: Bearer <token>`.

## Public

- `GET /health`
- `POST /auth/register`
- `POST /auth/login`
- `GET /availability/**`
- `GET /v3/api-docs`
- `GET /swagger-ui.html`

## Customer

- `GET /reservations`
- `GET /reservations/{id}`
- `POST /reservations`
- `POST /reservations/{id}/cancel`

## Front desk or admin

- `GET /front-desk/arrivals`
- `GET /front-desk/departures`
- `POST /front-desk/reservations/{id}/check-in`
- `POST /front-desk/reservations/{id}/check-out`
- `POST /front-desk/reservations/{id}/no-show`

## Admin

- Hotel, room-type, and room CRUD under `/admin/**`
- `GET /admin/inventory`
- `POST /admin/inventory/initialize`

Reservation mutation endpoints are scaffolded; their core business logic intentionally remains TODO.
