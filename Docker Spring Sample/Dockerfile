# syntax=docker/dockerfile:1

#### Stage 1: build Spring Boot application using Maven cache-friendly layers
FROM maven:3.9.11-sapmachine-25 AS build
WORKDIR /workspace

# Pre-fetch dependencies to leverage Docker layer caching when source changes are minimal
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn -B dependency:go-offline

# Copy source separately to avoid busting dependency cache
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B package -DskipTests

#### Stage 2: runtime on slim Debian with non-root user
FROM debian:trixie-slim

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# Install minimal JRE and create non-root user for running the app
RUN apt-get update \
    && apt-get install -y --no-install-recommends openjdk-21-jre-headless \
    && rm -rf /var/lib/apt/lists/* \
    && useradd --create-home --uid 1001 spring

WORKDIR /app

COPY --from=build /workspace/target/spring-sample-0.0.1-SNAPSHOT.jar ./spring-sample.jar

EXPOSE 80

USER spring

ENTRYPOINT ["java", "-jar", "/app/spring-sample.jar"]
