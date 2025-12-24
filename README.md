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

# Local Development

## Prerequisites
- Java 17
- Docker (for MySQL)
- Node.js 20+ (for frontend)

## Setup

### Start MySQL
```bash
docker compose up mysql -d
```

### Build the Project
```bash
./gradlew build
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
cd frontend && npm install && npm run dev
```

Access the app at http://localhost:5173

## Tests

### Run Tests
```bash
./gradlew test
```

### Clean build and tests
```bash
./gradlew clean build
```
