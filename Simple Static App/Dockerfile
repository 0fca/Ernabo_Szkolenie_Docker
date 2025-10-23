# syntax=docker/dockerfile:1

#### Stage 1: collect static assets and configuration
FROM alpine:3.20 AS assets
WORKDIR /assets

# Copy static site content; structured COPY keeps layer cache hits when content stays unchanged
COPY static/ ./static/
COPY config/default.conf ./default.conf

#### Stage 2: minimal nginx runtime
FROM nginx:1.29.2

# Copy built assets and nginx config from the previous stage
COPY --from=assets /assets/static /usr/share/nginx/html
COPY --from=assets /assets/default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
