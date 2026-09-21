# RS-01

Single-hotel reservation and operations backend implemented as a modular monolith.

## Technology

- Java 21, Spring Boot 3, Maven
- Spring Web, Spring Security, JWT, Validation
- MyBatis, MySQL 8
- springdoc-openapi / Swagger UI

## Modules

- `auth`, `security`: authentication and current-user infrastructure
- `user`: user persistence
- `hotel`: hotel, room type, and room management
- `inventory`: date-based room-type inventory
- `reservation`: reservations, nightly price snapshots, and front-desk operations
- `common`, `config`: shared results, exceptions, and configuration

## Data model

The main tables are `user`, `hotel`, `room_type`, `room`, `daily_inventory`, `reservation`, and `reservation_night`. Inventory is identified by `room_type_id + stay_date`; reservations are made for a room type and a room is assigned at check-in.

## Local startup

1. Run `sql/schema.sql` and then `sql/data.sql` against MySQL 8.
2. Set `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET` as needed.
3. Run `./mvnw spring-boot:run` (Windows: `.\mvnw.cmd spring-boot:run`).

The API starts on `http://localhost:8080`. Swagger UI is at `http://localhost:8080/swagger-ui.html`.

## Docker startup

```shell
docker compose up --build
```

Compose starts the application and MySQL and initializes Sakura Inn sample data. Defaults are development-only; override `DB_PASSWORD`, `MYSQL_ROOT_PASSWORD`, and `JWT_SECRET` outside local development.

## Main APIs

- `POST /auth/register`, `POST /auth/login`
- `GET /availability/**`
- `/reservations/**`
- `/front-desk/**`
- `/admin/**`

## Reservation states

`CONFIRMED → CHECKED_IN → CHECKED_OUT`, with `CONFIRMED → CANCELLED` and `CONFIRMED → NO_SHOW` side paths. The state-transition business logic remains developer-owned.

## V1 scope

V1 covers one hotel, room types, rooms, daily inventory, reservations, nightly price snapshots, authentication, and front-desk scaffolding. It excludes microservices, Redis, MQ, Elasticsearch, Spring Batch, and distributed transactions.

See `docs/architecture.md`, `docs/database.md`, and `docs/api.md` for concise references.
