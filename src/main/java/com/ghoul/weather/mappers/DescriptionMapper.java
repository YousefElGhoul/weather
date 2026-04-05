package com.ghoul.weather.mappers;

import java.util.Map;

public class DescriptionMapper {

    private static final Map<String, String> CONDITIONS = Map.ofEntries(
            Map.entry("01d", "fa-regular fa-sun"),
            Map.entry("01n", "fa-regular fa-moon"),
            Map.entry("02d", "fa-solid fa-cloud-sun"),
            Map.entry("02n", "fa-solid fa-cloud-moon"),
            Map.entry("03d", "fa-regular fa-cloud"),
            Map.entry("03n", "fa-regular fa-cloud"),
            Map.entry("04d", "fa-solid fa-cloud"),
            Map.entry("04n", "fa-solid fa-cloud"),
            Map.entry("09d", "fa-solid fa-cloud-showers-heavy"),
            Map.entry("09n", "fa-solid fa-cloud-showers-heavy"),
            Map.entry("10d", "fa-solid fa-cloud-sun-rain"),
            Map.entry("10n", "fa-solid fa-cloud-moon-rain"),
            Map.entry("11d", "fa-solid fa-cloud-bolt"),
            Map.entry("11n", "fa-solid fa-cloud-bolt"),
            Map.entry("13d", "fa-solid fa-snowflake"),
            Map.entry("13n", "fa-solid fa-snowflake"),
            Map.entry("50d", "fa-solid fa-smog"),
            Map.entry("50n", "fa-solid fa-smog")
    );

    private static final String DEFAULT = "fa-cloud-question";

    public static String getConditionIcon(String code) {
        return CONDITIONS.getOrDefault(code, DEFAULT);
    }

    public static String formatDescription(String input) {
        if (input == null || input.isEmpty()) return input;

        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
            result.append(" ");
        }

        return result.toString().trim();
    }
}
