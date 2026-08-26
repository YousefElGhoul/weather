# Weather API

A production-minded Spring Boot API for a weather UI. It resolves the caller's approximate location from their network address, retrieves current conditions and a six-day forecast, and returns a frontend-friendly JSON response.

## Highlights

- IP-based approximate geolocation through ip-api.com and weather from OpenWeatherMap One Call API 3.0
- Current conditions plus a six-day forecast in metric units
- Consistent JSON errors for invalid client data, provider failures, timeouts, and unexpected errors
- Bounded, 10-minute in-memory cache for successful weather-provider responses; it never stores client IPs
- OpenAPI documentation, Swagger UI, unit tests, and MVC tests that never call external providers

## Architecture

`WeatherController` obtains the address exposed by Spring's forwarded-header support and delegates to `WeatherService`. The service uses `GeolocationService` and the two HTTP clients, validates provider payloads, then maps provider DTOs to public API DTOs. Successful OpenWeatherMap payloads are cached locally by rounded coordinates. Entries expire after 10 minutes and are limited to 1,000.

## Requirements and local setup

- Java 21
- An OpenWeatherMap One Call API 3.0 key

The application uses Spring Boot **4.0.5**, Java 21, Maven, Spring MVC, Spring Cache/Caffeine, and springdoc-openapi 3.0.2.

```bash
cp .env.example .env
set -a; source .env; set +a
./mvnw spring-boot:run
```

| Variable | Required | Description |
|---|---:|---|
| `OWM_API_KEY` | Yes | OpenWeatherMap One Call API 3.0 key |

`OWM_API_KEY` is intentionally not stored in source control. `.env` is ignored by Git and only helps export it locally. Local loopback requests do not represent a public location; use a real client/proxy address when manually exercising geolocation.

## API documentation

- Swagger UI: [`/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)
- OpenAPI document: [`/v3/api-docs`](http://localhost:8080/v3/api-docs)

All public endpoints are `GET` endpoints under `/api/v1`.

| Endpoint | Description |
|---|---|
| `/api/v1/mini-weather` | Current temperature and condition for the caller's approximate location |
| `/api/v1/full-weather` | Current details plus six forecast days for the caller's approximate location |

```bash
curl http://localhost:8080/api/v1/mini-weather
```

```json
{"description":"Clear Sky","temperature":28.5}
```

Error responses have one consistent shape:

```json
{"timestamp":"2026-08-26T15:00:00Z","status":504,"error":"Upstream timeout","message":"The weather provider did not respond in time.","path":"/api/v1/mini-weather"}
```

| Status | Meaning |
|---:|---|
| 200 | Weather response returned |
| 400 | Missing or invalid client address |
| 502 | A provider returned an invalid response or failed |
| 504 | A provider did not respond before the configured timeout |
| 500 | Unexpected server error |

## Tests

```bash
./mvnw verify
```

Tests mock service/provider boundaries and require neither an API key nor network access.

## Docker

```bash
docker build -t weather-api .
docker run --rm -p 8080:8080 -e OWM_API_KEY=your-key weather-api
```

The image runs as a non-root `spring` user. Its build stage executes the test suite before producing the runtime image.

## CI and deployment

GitHub Actions runs `./mvnw -B verify` before Cloud Run authentication, image push, or deployment. Deployment uses Workload Identity Federation and requires `GCP_PROJECT_ID`, `GAR_REPOSITORY`, `CLOUD_RUN_SERVICE_NAME`, `WIF_PROVIDER`, `WIF_SERVICE_ACCOUNT`, and `OWM_API_KEY` GitHub secrets. A deployment succeeds only when Cloud Run accepts the built image.

Geolocation is approximate and can reflect VPN or ISP routing. CORS defaults target the existing frontend and local development; adjust them for another frontend domain.
