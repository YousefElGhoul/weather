package com.ghoul.weather.mappers;

public class FlagMapper {

    public static String toFlagIconClass(String code) {
        return "fi fi-" + code.toLowerCase();
    }

    private static final String DEFAULT = "fi fi-xx fis";

    public static String getFlag(String code) {
        if (code == null || code.isEmpty()) return DEFAULT;
        else if (code.length() != 2) return DEFAULT;
        else if (!code.matches("[A-Z]{2}")) return DEFAULT;
        else return toFlagIconClass(code);
    }
}
