# RS-01 Architecture Rules

RS-01 is a single-process modular monolith.

Business modules are Java packages inside one Spring Boot application.

Do NOT introduce microservices or distributed architecture unless explicitly requested.

Package organization:

- package by feature/domain
- layer inside feature

Core domains:

- user
- hotel
- inventory
- reservation

Layering:

Controller → Service → Mapper → MySQL

Controller must remain thin.

Service owns:

- business rules
- authorization
- transaction boundaries
- state transitions
- orchestration

Mapper owns persistence only.

Do not create one Service per database table.

Do not trust frontend supplied:

- userId
- price
- totalAmount
- inventory
- status

Reservation inventory is date-based.

Inventory identity: `roomTypeId + stayDate`

Do not introduce:

- Spring Cloud
- Nacos
- Gateway
- Feign
- RPC
- MQ
- distributed transactions

unless explicitly requested.

Do not introduce:

- Redis
- Spring Batch
- Elasticsearch

until a real business requirement needs them.

V1 focuses on:

- Hotel
- RoomType
- Room
- DailyInventory
- Reservation
- ReservationNight

High-value business logic should not be automatically generated unless explicitly requested.

Core business logic must remain developer-owned.

Codex may implement:

- CRUD
- Entity
- DTO
- VO
- boilerplate Mapper
- Controller boilerplate
- SQL boilerplate
- validation
- configuration

Codex must NOT automatically implement:

- inventory algorithm
- concurrency control
- transaction boundaries
- CAS
- reservation state transitions
- check-in/check-out business rules
- cancellation restore logic

unless explicitly requested.

Developer will personally participate in:

- availability calculation
- date inventory deduction
- concurrent reservation
- reservation state machine
- cancellation inventory restore
- check-in/check-out
- transaction boundaries

Agent may handle:

- DTO
- VO
- Entity boilerplate
- Mapper CRUD
- Controller boilerplate
- SQL boilerplate
- validation
- frontend integration
- repetitive tests

Frontend style:

- minimal Japanese hotel UI
- anime-inspired accents only
- business usability first
- avoid excessive animation
- keep components simple
- API calls through unified request layer
- do not invent backend business rules
