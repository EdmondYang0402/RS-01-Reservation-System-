# RS-01 V1 Manual Test

Run `sql/schema.sql` and `sql/data.sql`, then start the backend and frontend with mock mode disabled:

```text
VITE_USE_MOCK=false
VITE_API_BASE_URL=/api
```

Development seed accounts:

| Role | Username | Password |
| --- | --- | --- |
| CUSTOMER | `customer` | `Customer123!` |
| FRONT_DESK | `frontdesk` | `FrontDesk123!` |
| ADMIN | `admin` | `Admin123!` |

These credentials are local test data only. The database stores BCrypt hashes.

## A. Registration, login, and JWT

1. Register a new customer from `/register`.
2. Log in with the registered username and password.
3. Confirm that the login response contains `token`, `userId`, `username`, and `role=CUSTOMER`.
4. Confirm subsequent `/reservations` requests include `Authorization: Bearer <token>`.
5. Remove or corrupt the token and confirm an authenticated request returns 401 and redirects to login.
6. Use a CUSTOMER token on `/admin/**` and confirm it returns 403.

## B. Availability and reservation creation

1. Search `/availability` with `checkInDate`, `checkOutDate`, and `guestCount` in `yyyy-MM-dd` format.
2. Select a returned room type and submit only `roomTypeId`, dates, guest name, guest phone, and guest count.
3. Record the returned reservation ID and number.
4. Verify `reservation.user_id` is the authenticated user, and verify backend-generated `status` and `total_amount`.
5. Verify `reservation_night` contains one row per stay date with price snapshots.
6. Verify the affected `daily_inventory` rows. Do not edit expected deduction rules in this checklist; compare them with the developer-owned implementation.

## C. Cancellation and inventory restoration

1. Create a CONFIRMED reservation.
2. Call `POST /reservations/{id}/cancel` as its customer owner.
3. Verify the reservation becomes CANCELLED.
4. Verify each affected daily inventory row is restored according to the developer-owned implementation.

## D. Check-in

1. Log in as FRONT_DESK or ADMIN and open today's arrivals.
2. Open the check-in dialog and select an available room of the reservation's room type.
3. Confirm the request body contains only `roomId`; the reservation ID remains in the URL.
4. Verify the reservation becomes CHECKED_IN, `assigned_room_id` is set, and the room becomes OCCUPIED.

## E. Check-out

1. Open today's departures for a CHECKED_IN reservation.
2. Submit check-out.
3. Verify the reservation becomes CHECKED_OUT and the assigned room becomes AVAILABLE.

## F. No-show

1. Select a CONFIRMED arrival.
2. Submit the no-show action.
3. Verify the reservation becomes NO_SHOW and inspect inventory according to the developer-owned rule.

## G. Repeated and invalid actions

Verify each operation fails without partial database changes:

- cancel the same reservation twice;
- check in the same reservation twice;
- check out the same reservation twice;
- perform an action with a role that is not allowed;
- access another customer's reservation.

## H. Concurrent booking of the last room

1. Prepare one room type and stay-date range with exactly one remaining unit.
2. Authenticate two different CUSTOMER users.
3. Submit both reservation requests concurrently with identical dates and room type.
4. Record both HTTP responses and transaction timestamps.
5. Inspect `reservation`, `reservation_night`, and every affected `daily_inventory` row.
6. Verify the result against the developer-owned concurrency and no-oversell expectations. Do not change CAS or transaction code as part of this manual test.

## Admin smoke test

1. Log in as ADMIN.
2. Query and edit Sakura Inn.
3. Query, create, edit, enable, and disable room types and rooms using only ordinary CRUD screens.
4. Query inventory by room type and date range.
5. Initialize a missing future range twice and confirm the second call does not overwrite existing rows.
