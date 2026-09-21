# Architecture

RS-01 is a single-process modular monolith: one Spring Boot application, JVM, Spring context, port, and MySQL database.

Packages are organized by feature (`user`, `hotel`, `inventory`, `reservation`) with layers inside each feature:

`Controller → Service → Mapper → MySQL`

Controllers handle HTTP and validation only. Services own rules, authorization, orchestration, transactions, and state transitions. Mappers own persistence only.

Spring Security uses stateless JWT authentication. `JwtAuthenticationFilter` populates `SecurityContextHolder`; `CurrentUserService` reads the current identity from it.

Reservation creation, inventory deduction/restoration, concurrency control, room assignment, and state transitions remain developer-owned. V1 does not use microservices, Redis, MQ, Elasticsearch, Spring Batch, or distributed transactions.
