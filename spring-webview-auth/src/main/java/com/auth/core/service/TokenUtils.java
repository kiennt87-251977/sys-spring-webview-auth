package com.auth.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtils {

    private static Map<String, String> mapTokenCache = new HashMap<>();

    public static String generateToken(String user, String pass, String bodyInput) {
        String token = user + "-" + pass + "123456789" + "_" + UUID.randomUUID().toString();
        mapTokenCache.put(user, token);
        return token;
    }

    public static boolean validateToken(String tokenInput) {
        try {
            String[] list = tokenInput.split("-");
            String token = mapTokenCache.get(list[0]);
            if (tokenInput.equals(token)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }


    public static String removeToken(String user) {
        String response = mapTokenCache.remove(user);
        return response;
    }
}
