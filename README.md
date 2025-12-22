# Decathlon Points Calculator

### Start MySQL
```bash
docker compose up -d
```
MySQL runs on port 3336

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

### Run Tests
```bash
./gradlew test
```
