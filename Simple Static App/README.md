# Simple Static Nginx Container

This example shows how to package a static web page with Nginx inside a Docker container. The Dockerfile uses a multi-stage build to keep the runtime image lean and switches to the non-root `nginx` user for better security. Nginx listens on port `6080`.

## Build

```sh
docker build -t simple-static-nginx:latest .
```

To add a versioned tag:

```sh
docker build -t simple-static-nginx:1.0 -t simple-static-nginx:latest .
```

## Run

Expose the container port 80 to your local 6080 and mount the `static` directory for easy authoring during development:

```sh
docker run --rm -p 6080:80 \
  -v "$(pwd)/static:/usr/share/nginx/html:ro" \
  simple-static-nginx:latest
```

Visit `http://localhost:6080` in your browser to confirm the page loads.
