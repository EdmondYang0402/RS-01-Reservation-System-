# Database

MySQL 8 uses InnoDB and `utf8mb4`. Run `sql/schema.sql` before `sql/data.sql`.

| Table | Purpose |
| --- | --- |
| `user` | Login identity and role |
| `hotel` | Single-hotel profile |
| `room_type` | Sellable room categories and base prices |
| `room` | Physical rooms assigned at check-in |
| `daily_inventory` | Inventory per room type and stay date |
| `reservation` | Reservation header and lifecycle status |
| `reservation_night` | Per-night date and price snapshot |

Key uniqueness rules are `daily_inventory(room_type_id, stay_date)`, `reservation.reservation_no`, and `reservation_night(reservation_id, stay_date)`. Foreign keys follow user/hotel/room-type/room/reservation ownership.

`data.sql` initializes Sakura Inn, Single Room and Twin Room types, six rooms, and fourteen days of daily inventory. It is development seed data only.
