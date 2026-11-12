<!--
---
portfolio:
  title: "Weather"
  subtitle: "Location-Aware Weather API and Dashboard"
  type:
    - Backend
    - Web
  status: "Maintained"
  time: "2026"

  description: "A Spring Boot weather service that resolves approximate client location, retrieves current conditions and forecasts from external providers, and exposes a frontend-friendly REST API with caching and structured error handling, paired with a lightweight dashboard that shows current conditions and a weekly forecast."

  skills:
    - Java
    - Spring Boot
    - Spring MVC
    - REST APIs
    - Caffeine
    - External APIs
    - OpenAPI
    - Docker
    - JUnit
    - GitHub Actions
    - Google Cloud Run
    - JavaScript
    - HTML
    - CSS
    - GitHub Pages

  live: "https://yousefelghoul.dev/weather/"
  slug: "weather-api"
---
-->

# Weather

A location-aware weather API and the dashboard that uses it.

| Part | Path | Stack | Live |
|---|---|---|---|
| API | [`be/weather`](be/weather) | Java 21, Spring Boot, Docker, Cloud Run | [Swagger UI](https://weather-api.yousefelghoul.me/swagger-ui/index.html) |
| Dashboard | [`fe`](fe) | HTML, CSS, JavaScript, GitHub Pages | [yousefelghoul.dev/weather](https://yousefelghoul.dev/weather/) |

## How it fits together

The dashboard calls `GET /api/v1/full-weather`. The API resolves the caller's approximate location from their network address, fetches current conditions and a six-day forecast from OpenWeatherMap, and returns one frontend-friendly JSON response.

## Deployment

Each part deploys by itself when its folder changes on `main`:

- `be/weather/**` runs [`deploy-cloud-run.yml`](.github/workflows/deploy-cloud-run.yml): tests, Docker Hub image push, Cloud Run deploy.
- `fe/**` runs [`deploy-pages.yml`](.github/workflows/deploy-pages.yml): publishes the folder to GitHub Pages.

See each folder's README for setup and details.

## License

[MIT](LICENSE.md)
