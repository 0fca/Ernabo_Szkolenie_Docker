# Docker Compose Notebook Example

This sample delivers a full Spring Boot CRUD application backed by PostgreSQL and rendered with JSP. The runtime container is Debian-based, uses a non-root user, and the docker-compose stack demonstrates bind mounts, an overlay2-style named volume, and a custom bridged network with a fixed subnet.

## Application Highlights

- Spring Boot 3.3 serving JSP views with validation-driven forms.
- PostgreSQL persistence using Spring Data JPA and Flyway migrations (schema and seed data).
- Multi-stage Dockerfile builds the fat jar with Maven before copying it into a slim Debian runtime.
- Docker Compose publishes the app on host port `6090`, wires the app to PostgreSQL, and configures volumes/networking per requirements.

## Prerequisites

- Docker Engine 24+
- Docker Compose v2
- Java 21 & Maven 3.9+ (for local builds/tests)

## Build & Test Locally

```sh
mvn clean verify
```

This compiles the project, runs tests, and prepares `target/notebook-app-0.0.1-SNAPSHOT.jar`.

## Build the Container Image

```sh
docker build -t notebook-app:latest .
```

To include a version tag:

```sh
docker build -t notebook-app:1.0.0 -t notebook-app:latest .
```

## Compose Stack

The compose specification lives in `docker-compose.yml` and defines:

- **Bind mount:** `./logs/app -> /app/logs` to inspect runtime logs without rebuilding the image.
- **Overlay volume:** `app-overlay-cache` uses the `overlay` driver with directories located under `overlay/` in the repo (make sure `${PWD}` resolves to the project directory when invoking Compose).
- **Bridged network:** `app-net` uses subnet `10.0.0.0/29`, reserving static IPs for `app` and `db`.

Before launching, ensure the overlay directories exist (already included) and, if your shell does not export `PWD`, set it manually:

```sh
export PWD="$(pwd)"
```

Now start the stack:

```sh
docker compose up --build
```

The application will be available at [http://localhost:6090](http://localhost:6090). Log output lives in `logs/app`. The database data persists in the `postgres-data` named volume.

To tear down the stack while keeping volumes:

```sh
docker compose down
```

Add `--volumes` to remove the PostgreSQL data.

## Database Migrations & Seeding

Flyway migrations reside in `src/main/resources/db/migration`:

1. `V1__create_notes_table.sql` creates the `notes` table and trigger to maintain timestamps.
2. `V2__seed_notes.sql` inserts a pair of sample notes so the UI is populated on first run.

Flyway executes automatically on application startup. You can add more migrations by following the naming convention `V<version>__<description>.sql`.

## Next Steps

- Edit JSP templates under `src/main/webapp/WEB-INF/jsp/notes` to customize the UI.
- Add more Flyway migrations for new features.
- If security scanning flags base-image vulnerabilities, pin newer Maven/JDK layers or rebuild from source per your organization policies.
