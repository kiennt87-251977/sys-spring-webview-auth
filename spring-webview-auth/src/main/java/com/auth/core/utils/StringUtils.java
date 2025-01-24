package com.auth.core.utils;

public class StringUtils {

    public static String limitStringLength(String input, int length) {
        if (input == null) {
            return null;
        } else if (input.length() <= length) {
            return input;
        } else {
            return input.substring(0, length) + " ... ";
        }
    }

    public static String limitResponseStringLength(String input) {
        return removeWhitespaces(limitStringLength(input, 400));
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

    public static StringBuilder removePasswordChar(String input) {
        StringBuilder output = new StringBuilder();
        if (input != null && !input.equals("null")) {
            String[] strings = input.split(" ");

            for (String elem : strings) {
                try {
                    if (elem.toLowerCase().contains("password=")) {
                        output.append(" password******");
                    } else {
                        output.append(" " + elem);
                    }
                } catch (Exception ignored) {
                }

            }
        }
        return output;
    }
}
