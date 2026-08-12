[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=jjrdizon_sample-address-book-service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=jjrdizon_sample-address-book-service) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jjrdizon_sample-address-book-service&metric=coverage)](https://sonarcloud.io/summary/new_code?id=jjrdizon_sample-address-book-service) [![Java CI](https://github.com/jjrdizon/sample-address-book-service/actions/workflows/ci.yaml/badge.svg)](https://github.com/jjrdizon/sample-address-book-service/actions/workflows/ci.yaml)

# Sample Address Book Service

A sample Spring Boot service for managing contacts and their phone numbers, built to demonstrate **hexagonal (ports & adapters) architecture** with a **contract-first REST API**.

The service is generated from an external [OpenAPI contract](https://github.com/jjrdizon/sample-address-book-api), keeping the API definition decoupled from the implementation.

## Features

- Create a contact with a name and one or more phone numbers
- List all contacts
- Contract-first API — controllers implement interfaces generated from an OpenAPI spec at build time
- PostgreSQL persistence with Flyway-style versioned SQL migrations
- Clean separation between domain logic, application use cases, and infrastructure (web/persistence)

## Architecture

The codebase follows **hexagonal architecture**, organized into three layers:

```
src/main/java/com/jjrdizon/sample/addressbook/service/
├── domain/                # Core business models and repository ports (framework-free)
│   ├── model/              # Contact, Name, ContactNumber
│   └── port/                # ContactRepository interface
├── application/            # Use cases orchestrating domain logic
│   ├── port/                 # CreateContactUseCase, GetContactsUseCase
│   └── usecase/               # Implementations
└── infrastructure/         # Adapters connecting the domain to the outside world
    ├── adapter/in/          # REST controller (implements generated OpenAPI interface) + DTO mapping
    ├── adapter/out/          # JPA entities, repository, and persistence mapping
    └── configuration/       # Spring wiring
```

The **domain layer** has no framework dependencies. The **application layer** defines use case ports and their implementations. The **infrastructure layer** contains "driving" adapters (the REST controller) and "driven" adapters (the JPA repository) that plug into the domain through ports — allowing persistence or transport to be swapped without touching business logic.

## Tech Stack

| Concern | Technology |
|---|---|
| Language / Runtime | Java 23 |
| Framework | Spring Boot 3.3.2 (Web, Data JPA, Validation) |
| Database | PostgreSQL 16 |
| Migrations | Flyway |
| API contract | OpenAPI 3, generated via `openapi-generator-maven-plugin` |
| Object mapping | MapStruct |
| Boilerplate reduction | Lombok |
| Testing | JUnit / Spring Boot Test, AssertJ, H2 (test DB) |
| Coverage | JaCoCo → SonarCloud |
| Containerization | Docker (Eclipse Temurin JRE 23, Alpine) |
| Deployment | Helm chart included (`charts/sample-address-book-service`) |
| CI | GitHub Actions (build, test, SonarQube scan) |

## Prerequisites

- Java 23 (or use the included Maven wrapper)
- Docker and Docker Compose
- Make (optional, for the provided shortcuts)

## Getting Started

The fastest way to run the full stack (app + PostgreSQL + migrations) locally is with Docker Compose:

```bash
make up
```

This builds the jar, then starts the database, runs Flyway migrations, and starts the app via `tools/local-docker-compose.yaml`. The API will be available at `http://localhost:8080`.

To stop everything:

```bash
make down
```

### Running without Docker

Build and run tests with the Maven wrapper:

```bash
./mvnw clean verify
```

This also downloads the latest OpenAPI spec and generates the API interfaces/DTOs into `target/generated-sources` before compiling.

To run the app directly, point it at a running PostgreSQL instance via `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD`, then:

```bash
./mvnw spring-boot:run
```

## API

Endpoints are defined by the external OpenAPI contract and implemented in `ContactsV1Controller`:

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/v1/contacts` | Create a new contact |
| `GET`  | `/v1/contacts` | List all contacts |

Exact request/response schemas come from the [OpenAPI spec](https://github.com/jjrdizon/sample-address-book-api).

## Database

Schema is managed with versioned SQL migrations in `db/migration/`:

- `V1__init.sql` — creates the `contacts` table
- `V2__contact_number_list.sql` — creates the `contact_numbers` table, linked to `contacts`

Migrations run automatically against the `db` service when using `make up`.

## Testing

```bash
./mvnw clean verify
```

Runs unit and integration tests (via `maven-surefire`/`maven-failsafe`) with JaCoCo coverage reporting.

## Deployment

A Helm chart is provided under `charts/sample-address-book-service/` for deploying to Kubernetes, along with a `Dockerfile` for building the container image.

## Project Status

This is a sample/learning project intended to demonstrate hexagonal architecture patterns in a Spring Boot service — not intended for production use as-is.
