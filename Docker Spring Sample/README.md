# Spring Boot Docker Sample

Simple Spring Boot application packaged into a Debian-based Docker image. The app serves a static HTML layout and a CSS stylesheet through REST endpoints, demonstrating containerized web delivery without any external dependencies.

## Prerequisites

- Java 21 (for local builds/tests)
- Maven 3.9+
- Docker and Docker Compose

## Build Locally

```sh
mvn clean package
```

Run tests while building:

```sh
mvn verify
```

## Build Container Image

```sh
docker build -t spring-sample:latest .
```

Providing a versioned tag:

```sh
docker build -t spring-sample:1.0 -t spring-sample:latest .
```

## Run with Docker

Map container port 80 to host 6090:

```sh
docker run --rm -p 6090:80 spring-sample:latest
```

Visit `http://localhost:6090` to see the rendered page.
