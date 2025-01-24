package com.web.registry.utils;

public class StringUtils {

    public static String limitStringLength(String input, int length) {
        if (input == null) {
            return null;
        } else if (input.length() <= length) {
            return input;
        } else {
            return input.substring(0, length)+" ... ";
        }
    }

    public static String limitResponseStringLength(String input) {
        return removeWhitespaces(limitStringLength(input, 300));
    }


    public static String removeWhitespaces(String json) {
        boolean quoted = false;
        boolean escaped = false;
        boolean linefeed = false;
        String out = "";
        for (Character c : json.toCharArray()) {
            if (escaped) {
                out += c;
                escaped = false;
                continue;
            }
            if (c == '"') {
                quoted = !quoted;
            } else if (c == '\\') {
                escaped = true;
            }
            if (c == ' ' & !quoted) {
                continue;
            }
            if (c == '\r' & !quoted) {
                continue;
            }
            if (c == '\n' & !quoted) {
                continue;
            }
            out += c;
        }
        return out;
    }
}
