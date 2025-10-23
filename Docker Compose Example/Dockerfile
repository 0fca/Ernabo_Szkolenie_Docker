# syntax=docker/dockerfile:1

FROM maven:3.9.11 AS build
WORKDIR /workspace

COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn -B dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B package -DskipTests

FROM debian:bookworm-slim

ENV JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto
ENV PATH="$JAVA_HOME/bin:$PATH"
ENV SPRING_PROFILES_ACTIVE=prod

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl unzip ca-certificates \
    && curl -fsSL https://corretto.aws/downloads/latest/amazon-corretto-21-x64-linux-jdk.tar.gz -o /tmp/corretto.tar.gz \
    && mkdir -p /usr/lib/jvm \
    && tar -xzf /tmp/corretto.tar.gz -C /usr/lib/jvm \
    && mv /usr/lib/jvm/amazon-corretto-21* "$JAVA_HOME" \
    && rm -rf /var/lib/apt/lists/* /tmp/corretto.tar.gz \
    && useradd --create-home --shell /bin/bash --uid 1001 spring

WORKDIR /app

COPY --from=build /workspace/target/notebook-app-0.0.1-SNAPSHOT.jar ./app.jar

RUN chown -R spring:spring /app /app
RUN mkdir /app/cache

USER spring

EXPOSE 80

CMD ["java", "-jar", "/app/app.jar"]
