# Notes API - Clean Architecture Implementation

This project demonstrates the implementation of **Clean Architecture** in a **Spring Boot** application for a **Notes API**. The focus of the architecture is to ensure **modularity**, **testability**, and **maintainability** by following the principles of **separation of concerns** and **decoupling**.

## Table of Contents

1. [Overview](#overview)
2. [Project Structure](#project-structure)
3. [Architecture](#architecture)
4. [Setup](#setup)
5. [Clean Architecture Principles](#clean-architecture-principles)

---

## Overview

This project provides a simple implementation of a **Create User** feature using **Clean Architecture** principles. It is designed to ensure that business logic is **decoupled** from frameworks, infrastructure, and web concerns, which improves maintainability and scalability.

### Key Features:

* User creation with validation
* Use of **Clean Architecture** to separate concerns
* Spring Boot for the backend and PostgreSQL for data persistence
* Docker setup for PostgreSQL container management

---

## Project Structure

The project is divided into four main layers that follow the **Clean Architecture** pattern:

```
lfss_notes_api/
├── application/             # Application layer (use cases)
│   ├── usecases/            # Use cases for business logic
│   ├── ports/               # Interfaces (ports) defining operations
├── domain/                  # Domain layer (business logic)
│   ├── model/               # Domain models (entities like User)
│   ├── service/             # Domain services and policies (optional)
│   ├── errors/              # Custom domain exceptions
├── infrastructure/          # Infrastructure layer (frameworks and external systems)
│   ├── persistence/         # JPA repositories, database interaction
│   ├── system/              # Infrastructure services like UUID generation, clocks
├── web/                     # Web layer (controllers, HTTP handling)
│   ├── controller/          # REST controllers
│   ├── exception/           # Exception handling
└── lfssNotesApiApplication.java  # Main entry point of the application
```

---

## Architecture

### Domain Layer (`domain/`)

* **Purpose**: Contains core business logic and domain entities. It is independent of frameworks or external systems.
* **Components**:

    * **Entities**: Represent business concepts. Example: `User.java`.
    * **Services**: Encapsulate business rules. Example: `UserPolicy.java`.
    * **Exceptions**: Handle domain-specific errors. Example: `UserAlreadyExistsException.java`.

### Application Layer (`application/`)

* **Purpose**: Orchestrates use cases, interacting with the domain and infrastructure layers.
* **Components**:

    * **Use Cases**: Represent business actions (e.g., `CreateUserUseCase.java`).
    * **Ports**: Define abstract interfaces that the application uses to interact with external systems (e.g., `UserRepositoryPort.java`).

### Infrastructure Layer (`infrastructure/`)

* **Purpose**: Provides implementations for external systems, such as databases and external services.
* **Components**:

    * **Repositories**: Implement data access using technologies like JPA.
    * **Mappers**: Convert domain objects to persistence entities.
    * **UUID Generators and Clocks**: Provide utility services like unique ID generation.

### Web Layer (`web/`)

* **Purpose**: Handles HTTP requests and responses, acting as an interface adapter.
* **Components**:

    * **Controllers**: Expose RESTful endpoints. Example: `UserController.java`.
    * **DTOs**: Define data transfer objects for HTTP payloads. Example: `CreateUserRequest.java` and `CreateUserResponse.java`.
    * **Exception Handling**: Maps exceptions to appropriate HTTP responses.

---

## Setup

### Prerequisites

* **Docker**: Ensure Docker is installed to run the PostgreSQL container.
* **Java 21+**: Required for building and running the Spring Boot application.
* **Maven**: Dependency management and build tool.

### Running the Application

1. **Start the PostgreSQL Database**:

    * Run the following command to start the database container using Docker:

   ```bash
   docker-compose up --build
   ```

2. **Run the Spring Boot Application**:

    * After the database container is up, run the Spring Boot application:

   ```bash
   mvn spring-boot:run
   ```

    * The application will be available at `http://localhost:8080`.

---

## Clean Architecture Principles

### 1. **Separation of Concerns**

Each layer of the architecture is **responsible** for a specific aspect of the application:

* **Domain layer**: Purely contains business logic.
* **Application layer**: Orchestrates use cases and defines the application’s business rules.
* **Infrastructure layer**: Manages interactions with external systems (e.g., databases, third-party services).
* **Web layer**: Exposes HTTP APIs for client interaction.

### 2. **Decoupling**

* The **domain logic** is **decoupled** from frameworks and technologies, making it easier to replace external systems (e.g., changing from PostgreSQL to MongoDB) without affecting business rules.
* Use cases are defined **independently** of the web framework, so they can be reused in different contexts (e.g., a desktop client or an API).

### 3. **Interfaces as Ports**

* The **application layer** defines **abstract interfaces** (ports) that specify the operations the application can perform.
* The **infrastructure layer** provides the implementation of these interfaces (adapters).

### 4. **Testability**

Each layer can be **unit tested** independently, and **mocked dependencies** can be used to test the logic in isolation. For example:

* Test the **use case** without worrying about the database by mocking the repository.
* Test the **web layer** by mocking the service layer.

### 5. **Maintainability**

By isolating concerns into layers, it becomes easier to modify or replace components:

* Change the database layer without touching the domain logic.
* Replace the web framework (e.g., switch from REST to GraphQL) with minimal changes.

---
