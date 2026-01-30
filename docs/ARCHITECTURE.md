# Architecture & Create User Workflow

This document describes the **Clean Architecture** used in this API and the **complete request-to-database workflow** for the Create User feature.

---

## Table of Contents

1. [Clean Architecture Overview](#clean-architecture-overview)
2. [Layers and Responsibilities](#layers-and-responsibilities)
3. [Dependency Rule](#dependency-rule)
4. [Create User: Complete Workflow](#create-user-complete-workflow)
5. [Data Flow and DTOs](#data-flow-and-dtos)
6. [Package Map](#package-map)

---

## Clean Architecture Overview

The application is structured in **four concentric layers**. Dependencies point **inward**: outer layers depend on inner layers, never the other way around. The **domain** has no dependencies; **application** (use cases) depends only on the domain; **infrastructure** and **presentation** implement interfaces defined by the application and translate between external formats and domain/application types.

```
                    ┌─────────────────────────────────────┐
                    │           PRESENTATION              │
                    │  (REST controllers, DTOs, validation)│
                    └─────────────────┬───────────────────┘
                                      │ uses
                                      ▼
                    ┌─────────────────────────────────────┐
                    │           APPLICATION                │
                    │  (use cases, ports in/out, commands) │
                    └─────────────────┬───────────────────┘
                                      │ uses
                    ┌─────────────────▼───────────────────┐
                    │              DOMAIN                  │
                    │  (entities, business rules only)     │
                    └─────────────────────────────────────┘
                                      ▲
                    ┌─────────────────┴───────────────────┐
                    │          INFRASTRUCTURE              │
                    │  (JPA, DB adapters, implements ports)│
                    └─────────────────────────────────────┘
```

---

## Layers and Responsibilities

| Layer | Purpose | Key types | Depends on |
|-------|---------|-----------|------------|
| **Domain** | Core business concepts; no frameworks. | `User` entity | Nothing |
| **Application** | Use cases and contracts (ports). Orchestrates domain and outbound adapters. | `CreateUserUseCase`, `SaveUserPort`, `CreateUserCommand`, `CreateUserService` | Domain |
| **Infrastructure** | Implements outbound ports; talks to DB, external APIs, etc. | `UserJpaEntity`, `UserJpaRepository`, `UserPersistenceAdapter` | Application (ports), Domain |
| **Presentation** | HTTP entrypoint; validation; maps HTTP ↔ application. | `UserController`, `CreateUserRequest`, `UserResponse`, `ValidationExceptionHandler` | Application (use cases), Domain (for response mapping) |

- **Ports** = interfaces at the application boundary. **Inbound ports** (e.g. `CreateUserUseCase`) are called by the presentation layer. **Outbound ports** (e.g. `SaveUserPort`) are implemented by the infrastructure layer.
- **Adapters** = implementations of ports. The controller is an inbound adapter (HTTP → use case); the persistence adapter is an outbound adapter (use case → database).

---

## Dependency Rule

- **Domain** does not depend on any other layer.
- **Application** depends only on **domain**; it defines **ports** (interfaces) for persistence and other side effects.
- **Infrastructure** implements **outbound ports** and uses **domain** entities when fulfilling those contracts; it does not drive use cases.
- **Presentation** calls **inbound ports** (use cases) and converts HTTP request/response to **application** DTOs and **domain**-based responses.

So: **request** enters via presentation → application (use case) → domain; **persistence** is invoked by the use case via an outbound port implemented by infrastructure. The DB is never referenced from domain or application, only from infrastructure.

---

## Create User: Complete Workflow

End-to-end path for **POST /api/users** from the HTTP request to the database.

### Step-by-step

1. **HTTP request**  
   Client sends `POST /api/users` with JSON body: `{ "name": "alice", "password": "secret" }`.

2. **Presentation – Controller**  
   - `UserController.createUser()` receives the body as `CreateUserRequest` (presentation DTO).  
   - Bean Validation (`@Valid`) runs on `CreateUserRequest` (e.g. `@NotBlank`, `@Size(max=32)` on name).  
   - If validation fails, `ValidationExceptionHandler` returns **400** with error details.

3. **Presentation → Application**  
   - Controller builds a `CreateUserCommand(name, password)` (application DTO) and calls `CreateUserUseCase.create(command)`.

4. **Application – Use case**  
   - `CreateUserService` (implements `CreateUserUseCase`):  
     - Builds a **domain** `User` with a new `UUID`, `command.name()`, and `command.password()`.  
     - Calls **outbound port** `SaveUserPort.save(user)` to persist.

5. **Application → Infrastructure**  
   - `SaveUserPort` is implemented by `UserPersistenceAdapter` (infrastructure).  
   - The use case does not know about JPA or the database; it only depends on the port.

6. **Infrastructure – Persistence adapter**  
   - `UserPersistenceAdapter.save(user)`:  
     - Maps **domain** `User` → **JPA entity** `UserJpaEntity`.  
     - Calls `UserJpaRepository.save(entity)` (Spring Data JPA).  
     - Maps saved `UserJpaEntity` back to **domain** `User` and returns it to the use case.

7. **Database**  
   - Spring Data JPA / Hibernate issues `INSERT` into the `users` table (id UUID, name VARCHAR(32), password TEXT), as defined by `UserJpaEntity` and your schema (e.g. `database/init.sql`).

8. **Response path**  
   - Use case returns the saved **domain** `User` to the controller.  
   - Controller maps it to `UserResponse(id, name)` (no password) and returns **201 Created** with that JSON.

### Flow diagram (Create User)

```
Client
  │ POST /api/users { "name", "password" }
  ▼
┌──────────────────────────────────────────────────────────────────┐
│ PRESENTATION                                                      │
│  UserController.createUser(CreateUserRequest)                     │
│    @Valid → ValidationExceptionHandler on failure (400)           │
│    → CreateUserCommand(name, password)                            │
│    → createUserUseCase.create(command)                            │
└──────────────────────────────────────────────────────────────────┘
  │
  ▼
┌──────────────────────────────────────────────────────────────────┐
│ APPLICATION                                                       │
│  CreateUserService.create(CreateUserCommand)                       │
│    → new User(UUID.randomUUID(), name, password)   [DOMAIN]       │
│    → saveUserPort.save(user)                                      │
└──────────────────────────────────────────────────────────────────┘
  │
  ▼
┌──────────────────────────────────────────────────────────────────┐
│ INFRASTRUCTURE                                                    │
│  UserPersistenceAdapter.save(User)                                │
│    → User → UserJpaEntity                                          │
│    → userJpaRepository.save(entity)                               │
│    → UserJpaEntity → User (returned to use case)                  │
└──────────────────────────────────────────────────────────────────┘
  │
  ▼
┌──────────────────────────────────────────────────────────────────┐
│ DATABASE                                                          │
│  INSERT INTO users (id, name, password) VALUES (?, ?, ?)           │
└──────────────────────────────────────────────────────────────────┘

  Response: 201 Created, body: { "id": "<uuid>", "name": "alice" }
```

---

## Data Flow and DTOs

| Stage | Type | Role |
|-------|------|------|
| HTTP in | `CreateUserRequest` | Presentation: JSON binding + validation (name, password). |
| Controller → Use case | `CreateUserCommand` | Application: input for the use case (name, password). |
| Use case internal | `User` | Domain: entity with id, name, password. |
| Use case → Adapter | `User` | Passed to `SaveUserPort.save(User)`. |
| Adapter ↔ JPA | `UserJpaEntity` | Infrastructure: table mapping (id, name, password). |
| Use case → Controller | `User` | Domain entity returned from `save()`. |
| HTTP out | `UserResponse` | Presentation: JSON with id and name only (password never exposed). |

So the **workflow** is: **Request DTO → Command → Domain Entity → (optional) JPA Entity → Domain Entity → Response DTO**. The domain entity is the core object that crosses the application boundary in both directions for this flow.

---

## Package Map

```
lfssa.lfss_notes_api
├── domain
│   └── entity
│       └── User
├── application
│   ├── port
│   │   ├── in
│   │   │   └── CreateUserUseCase
│   │   └── out
│   │       └── SaveUserPort
│   ├── dto
│   │   └── CreateUserCommand
│   └── usecase
│       └── CreateUserService
├── infrastructure
│   └── persistence
│       ├── entity
│       │   └── UserJpaEntity
│       ├── repository
│       │   └── UserJpaRepository
│       └── adapter
│           └── UserPersistenceAdapter
└── presentation
    ├── controller
    │   ├── UserController
    │   └── ValidationExceptionHandler
    └── dto
        ├── CreateUserRequest
        └── UserResponse
```

---

## Summary

- **Architecture**: Clean Architecture with four layers (domain, application, infrastructure, presentation). Dependencies point inward; application defines ports, infrastructure and presentation implement or call them.
- **Create User workflow**: HTTP → Controller (validation, request DTO → command) → Use case (domain user, call `SaveUserPort`) → Persistence adapter (domain ↔ JPA entity, JpaRepository) → Database. Response is built from the returned domain user and mapped to `UserResponse`.
- **Database**: The `users` table (id UUID, name VARCHAR(32), password TEXT) is used via JPA in the infrastructure layer only; domain and application stay free of DB and framework details.
