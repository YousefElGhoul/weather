const iconElements = [
     day0Icon = document.getElementById("icon0"),
     day1Icon = document.getElementById("icon1"),
     day2Icon = document.getElementById("icon2"),
     day3Icon = document.getElementById("icon3"),
     day4Icon = document.getElementById("icon4"),
     day5Icon = document.getElementById("icon5"),
     day6Icon = document.getElementById("icon6")
]
const descriptionElements = [
    day0Description = document.getElementById("weatherDescription0"),
    day1Description = document.getElementById("weatherDescription1"),
    day2Description = document.getElementById("weatherDescription2"),
    day3Description = document.getElementById("weatherDescription3"),
    day4Description = document.getElementById("weatherDescription4"),
    day5Description = document.getElementById("weatherDescription5"),
    day6Description = document.getElementById("weatherDescription6"),
]
const temperatureElements = [
    day0Temp = document.getElementById("temperature0"),
    day1Temp = document.getElementById("temperature1"),
    day2Temp = document.getElementById("temperature2"),
    day3Temp = document.getElementById("temperature3"),
    day4Temp = document.getElementById("temperature4"),
    day5Temp = document.getElementById("temperature5"),
    day6Temp = document.getElementById("temperature6")
];
const feelsLikeElements = [
    day0FeelsLike = document.getElementById("feels-like0"),
    day1FeelsLike = document.getElementById("feels-like1"),
    day2FeelsLike = document.getElementById("feels-like2"),
    day3FeelsLike = document.getElementById("feels-like3"),
    day4FeelsLike = document.getElementById("feels-like4"),
    day5FeelsLike = document.getElementById("feels-like5"),
    day6FeelsLike = document.getElementById("feels-like6")
]
const lowElements = [
    day0Low = document.getElementById("min-temp0"),
    day1Low = document.getElementById("min-temp1"),
    day2Low = document.getElementById("min-temp2"),
    day3Low = document.getElementById("min-temp3"),
    day4Low = document.getElementById("min-temp4"),
    day5Low = document.getElementById("min-temp5"),
    day6Low = document.getElementById("min-temp6")
]
const highElements = [
    day0High = document.getElementById("max-temp0"),
    day1High = document.getElementById("max-temp1"),
    day2High = document.getElementById("max-temp2"),
    day3High = document.getElementById("max-temp3"),
    day4High = document.getElementById("max-temp4"),
    day5High = document.getElementById("max-temp5"),
    day6High = document.getElementById("max-temp6")
]

const midnightTemp = document.getElementById("0000")
const morningTemp = document.getElementById("0600")
const noonTemp = document.getElementById("1200")
const eveningTemp = document.getElementById("1800")

const city = document.getElementById("city")
const country = document.getElementById("country")

const weatherApi = 'https://weather-api.yousefelghoul.me/api/v1/full-weather'

function getWeather() {
    return fetch(weatherApi)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        });
}

function updateWeather() {
    getWeather()
        .then(json => {
            console.log(json);

            // location
            city.textContent = json.city;
            country.className = json.country_code;

            // today (first card)
            iconElements[0].className = json.today.icon_code;
            descriptionElements[0].textContent = json.today.description;
            temperatureElements[0].textContent = Math.round(json.today.temp.temp);
            feelsLikeElements[0].textContent = Math.round(json.today.temp.feels_like);
            lowElements[0].textContent = Math.round(json.today.temp.low);
            highElements[0].textContent = Math.round(json.today.temp.high);
            midnightTemp.textContent = Math.round(json.today.midnight);
            morningTemp.textContent = Math.round(json.today.morning);
            noonTemp.textContent = Math.round(json.today.noon);
            eveningTemp.textContent = Math.round(json.today.evening);

            // Forecast index 0 belongs in card 1 because card 0 is today.
            const forecastCount = Math.min(json.forecast.length, iconElements.length - 1);
            for (let i = 0; i < forecastCount; i++) {
                const day = json.forecast[i];
                const cardIndex = i + 1;
                iconElements[cardIndex].className = day.icon_code;
                descriptionElements[cardIndex].textContent = day.description;
                temperatureElements[cardIndex].textContent = Math.round(day.temperature.temp);
                feelsLikeElements[cardIndex].textContent = Math.round(day.temperature.feels_like);
                lowElements[cardIndex].textContent = Math.round(day.temperature.low);
                highElements[cardIndex].textContent = Math.round(day.temperature.high);
            }
        })
        .catch(error => {
            console.error('Error fetching weather data:', error);
        });
}
