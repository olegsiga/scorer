# Decathlon Points Calculator

## Prerequisites
- Java 17
- Docker
- Node.js (for frontend)

## In order to start application
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



## To Run tests
### Run Tests
```bash
./gradlew test
```

## Or
### Clean build and tests
```bash
./gradlew clean build
```
