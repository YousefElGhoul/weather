# Weather API

A Spring Boot REST API that serves as a backend for the [Weather](https://yousefelghoul.gitlab.io/weather) frontend. It automatically detects the user's location from their IP address and returns a 7-day weather forecast.

## Features

- **Automatic geolocation** — detects the user's location from their IP using ip-api.com, no input required
- **7-day forecast** — fetches daily weather data from OpenWeatherMap One Call API 3.0
- **Today's detailed breakdown** — returns midnight, morning, noon, and evening temperatures for the current day
- **Custom weather conditions** — maps OWM condition codes to Font Awesome icons and human-readable descriptions
- **Single endpoint** — `GET /weather` returns everything the frontend needs in one call
- **Deployed on Fly.io** at `https://ghoul-weather.fly.dev`

## Notes

- The API uses [ip-api.com](http://ip-api.com) for geolocation, which is approximate — accuracy varies by ISP and region
- Using a VPN will result in inaccurate weather data as the detected location will reflect the VPN server's location, not the user's