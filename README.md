# Decathlon Points Calculator

A Java-based web service API that calculates decathlon points with a React frontend.

## Task Overview

Calculate decathlon points based on sport event and result using the official IAAF scoring tables.

## Decathlon Scoring Formula

Each event uses the formula:

- **Track events** (running): `Points = A × (B − P)^C` where P = time in seconds
- **Field events** (jumps/throws): `Points = A × (P − B)^C` where P = result in meters/centimeters

### Event Constants (IAAF 2001 Scoring Tables)

| Event | Type | A | B | C | Unit |
|-------|------|---|---|---|------|
| 100m | Track | 25.4347 | 18 | 1.81 | seconds |
| Long Jump | Field | 0.14354 | 220 | 1.40 | centimeters |
| Shot Put | Field | 51.39 | 1.5 | 1.05 | meters |
| High Jump | Field | 0.8465 | 75 | 1.42 | centimeters |
| 400m | Track | 1.53775 | 82 | 1.81 | seconds |
| 110m Hurdles | Track | 5.74352 | 28.5 | 1.92 | seconds |
| Discus Throw | Field | 12.91 | 4 | 1.1 | meters |
| Pole Vault | Field | 0.2797 | 100 | 1.35 | centimeters |
| Javelin Throw | Field | 10.14 | 7 | 1.08 | meters |
| 1500m | Track | 0.03768 | 480 | 1.85 | seconds |

---

## Implementation Plan

### 1. API Design

Following REST conventions from CLAUDE.md:

#### Calculate and Store Result
```
POST /api/score/calculate
```

**Request:**
```json
{
  "sport": "Long Jump",
  "result": 7.76
}
```

**Response:**
```json
{
  "status": "ok",
  "id": "01JFXYZ123ABC",
  "sport": "Long Jump",
  "result": 7.76,
  "points": 1000
}
```

#### Get Result by ID
```
POST /api/score/get
```

**Request:**
```json
{
  "id": "01JFXYZ123ABC"
}
```

#### List Recent Results
```
POST /api/score/list
```

**Response:**
```json
{
  "status": "ok",
  "content": [
    { "id": "01JFXYZ123ABC", "sport": "Long Jump", "result": 7.76, "points": 1000 },
    { "id": "01JFXYZ456DEF", "sport": "100m", "result": 10.4, "points": 1000 }
  ]
}
```

**Error Response:**
```json
{ "status": "invalid-sport" }
{ "status": "result-not-found" }
```

### 2. Backend Architecture

```
server/src/main/java/com/scorer/
├── controller/
│   ├── HealthController.java
│   └── ScoreController.java
├── service/
│   └── ScoreService.java
├── dao/
│   └── ScoreResultDao.java
├── entity/
│   ├── Sport.java (enum with constants)
│   └── ScoreResult.java
└── ScorerApplication.java
```

#### Key Components

**Sport Enum:**
```java
public enum Sport {
    HUNDRED_METERS("100m", TrackType.TRACK, 25.4347, 18, 1.81),
    LONG_JUMP("Long Jump", TrackType.FIELD, 0.14354, 220, 1.40),
    // ... other events
}
```

**Service:**
- Calculation logic
- Results stored in MySQL (showcases DAO layer)
- Returns generated ID with each result

**DAO Layer:**
- `@Repository` with `NamedParameterJdbcTemplate`
- Proper SQL queries with `@Language("SQL")`
- Transaction management with `@Transactional`

### 3. Frontend (React)

Simple form with:
- Dropdown to select sport event
- Input field for result
- Submit button
- Display calculated points

```
frontend/src/
├── components/
│   └── ScoreCalculator.tsx
├── api/
│   └── scoreApi.ts
└── App.tsx
```

---

## High Load Considerations

### Current Design (MySQL Storage)

For database-backed storage:
- **Throughput**: Single instance ~5,000-10,000 req/sec (DB becomes bottleneck)
- **Persistence**: Data survives restarts
- **Bottleneck**: Database writes and connection pool

### Scaling Strategies

#### Level 1: Single Instance Optimization (up to ~50K req/sec)

1. **Virtual threads (Java 21+)**
   ```java
   spring.threads.virtual.enabled=true
   ```

2. **Tune thread pool**
   ```properties
   server.tomcat.threads.max=400
   server.tomcat.accept-count=200
   ```

3. **JVM tuning**
   ```
   -XX:+UseZGC -Xmx4g
   ```

#### Level 2: Horizontal Scaling (up to ~500K req/sec)

```
                    ┌─────────────────┐
                    │  Load Balancer  │
                    └────────┬────────┘
                             │
         ┌───────────────────┼───────────────────┐
         │                   │                   │
    ┌────▼────┐        ┌────▼────┐        ┌────▼────┐
    │ Server 1│        │ Server 2│        │ Server 3│
    └─────────┘        └─────────┘        └─────────┘
                             │
                    ┌────────▼────────┐
                    │   MySQL Primary │
                    │   + Read Replicas│
                    └─────────────────┘
```

**Database scaling options:**
- **Connection pooling**: HikariCP with optimal pool size
- **Read replicas**: For list/get operations (writes to primary only)
- **Async writes**: Queue writes, return immediately (eventual consistency)
- **Sharding**: Partition by ID range (for massive scale)

#### Level 3: Edge Computing (global scale)

For global low-latency:
- Deploy to edge locations (Cloudflare Workers, AWS Lambda@Edge)
- Since calculation is pure math, can be done at CDN edge
- ~10ms latency worldwide

### Caching (If Needed)

For repeated identical requests:
```java
@Cacheable("scores")
public int calculatePoints(Sport sport, double result) {
    // Cache key: sport + result (rounded to avoid float precision issues)
}
```

But for this use case, caching adds more overhead than computing:
- Calculation: ~0.001ms
- Cache lookup: ~0.01-0.1ms

**Recommendation**: Skip caching unless profiling shows otherwise.

---

## Development Setup

### Prerequisites
- Java 25
- Node.js 18+
- Docker

### Start MySQL
```bash
docker compose up -d
```

MySQL runs on port 3336 (to avoid conflicts with default 3306).

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
cd frontend
npm run dev
```

### Run Tests
```bash
./gradlew test
```

---

## Implementation Steps

1. Create `Sport` enum with all 10 events and constants
2. Create `DecathlonScoreService` with calculation logic
3. Create `ScoreController` with POST endpoint
4. Add input validation (result must be positive, sport must exist)
5. Add unit tests for all 10 events
6. Create React form component
7. Add API integration
8. Add error handling in UI

---

## Testing Strategy

### Unit Tests
- Test each event calculation against known values
- Edge cases: zero result, negative result, boundary values

### Integration Tests
- API endpoint tests with MockMvc
- Validate JSON request/response format

### Known Test Values
| Event | Result | Expected Points |
|-------|--------|-----------------|
| 100m | 10.395s | 1000 |
| Long Jump | 7.76m | 1000 |
| Shot Put | 18.40m | 1000 |
| 1500m | 3:53.79 | 1000 |
