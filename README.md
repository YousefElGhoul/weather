# Weather API

A Spring Boot REST API that serves as a backend for the [Weather](https://yousefelghoul.gitlab.io/weather) frontend. It automatically detects the user's location from their IP address and returns a 7-day weather forecast.

**Base URL:** `https://weather-api.yousefelghoul.me/` (production) / `http://localhost:8080` (local)

## Features

- **Automatic geolocation** — detects the user's location from their IP using ip-api.com, no input required
- **7-day forecast** — fetches daily weather data from OpenWeatherMap One Call API 3.0
- **Today's detailed breakdown** — returns midnight, morning, noon, and evening temperatures for the current day
- **Custom weather conditions** — maps OWM condition codes to Font Awesome icons and human-readable descriptions
- **Deployed on Google Cloud Run using GitHub Actions**

## Documentation

Interactive API documentation is available via Swagger UI:

- **Production:** [https://weather-api.yousefelghoul.me/swagger-ui/index.html](https://weather-api.yousefelghoul.me//swagger-ui/index.html)
- **Local:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

The OpenAPI 3 spec is also available at `/v3/api-docs`.

## Endpoints

All endpoints are prefixed with `/api/v1` and accept `GET` requests only.

| Endpoint | Description |
|---|---|
| `GET /api/v1/full-weather` | Full weather data for the caller's location: city, today's detailed weather, and 6-day forecast |
| `GET /api/v1/mini-weather` | Lightweight current conditions: description and current temperature only |
| `GET /api/v1/test-header` | Health-check endpoint, returns `"This is a Test"` |

### `GET /api/v1/full-weather`

Returns a complete weather report for the requester's location.

**Response `200 OK`**

```json
{
  "city": "Cairo",
  "country_code": "fi fi-eg",
  "today": {
    "icon_code": "fa-regular fa-sun",
    "description": "Clear Sky",
    "temp": {
      "temp": 28.5,
      "feels_like": 27.2,
      "low": 22.1,
      "high": 31.3
    },
    "midnight": 22.1,
    "morning": 24.3,
    "noon": 31.3,
    "evening": 28.5
  },
  "forecast": [
    {
      "icon_code": "fa-regular fa-sun",
      "description": "Clear Sky",
      "temperature": {
        "temp": 27.8,
        "feels_like": 26.5,
        "low": 21.4,
        "high": 30.1
      }
    }
  ]
}
```

The `forecast` array contains 6 entries (days 2–7), each with an `icon_code`, `description`, and `temperature` object.

### `GET /api/v1/mini-weather`

Returns a minimal current-conditions response.

**Response `200 OK`**

```json
{
  "description": "Clear Sky",
  "temperature": 28.5
}
```

### `GET /api/v1/test-header`

Simple health-check endpoint.

**Response `200 OK`**

```
This is a Test
```

## Errors

Errors are returned as JSON with the following structure:

```json
{
  "error": "Bad Gateway",
  "message": "Failed to resolve IP location"
}
```

| HTTP Status | Condition |
|---|---|
| `502 Bad Gateway` | IP geolocation failed |
| `404 Not Found` | OpenWeatherMap returned 404 (e.g. invalid coordinates) |
| `503 Service Unavailable` | OpenWeatherMap server error |
| `500 Internal Server Error` | Unexpected error |

## Tech Stack

- **Java 21** with **Spring Boot 3**
- **Maven** build system
- **Springdoc OpenAPI** for Swagger UI documentation

## Notes

- The API uses [ip-api.com](http://ip-api.com) for geolocation, which is approximate so accuracy varies by ISP and region
- Using a VPN will result in inaccurate weather data as the detected location will reflect the VPN server's location, not the user's
- API keys are injected via environment variables (`OWM_API_KEY`)
