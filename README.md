# Decathlon Points Calculator

## Run with Docker Compose

Build and run everything in Docker

```bash
docker compose up --build
```
Access the app at http://localhost:3000

To stop:
```bash
docker compose down
```

# Without docker compose

## Prerequisites
- Java 17
- Docker
- Node.js (for frontend)

## Local Development

### Start MySQL
```bash
docker compose up -d
```

### Run Database Migrations
```bash
./gradlew :database:update
```

### Run Backend
```bash
./gradlew bootRun
```

### Run Frontend
```bash
cd frontend && npm run dev
```

## Tests

### Run Tests
```bash
./gradlew test
```

### Clean build and tests
```bash
./gradlew clean build
```
