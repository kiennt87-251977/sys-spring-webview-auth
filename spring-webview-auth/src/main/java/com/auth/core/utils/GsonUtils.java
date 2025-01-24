package com.auth.core.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class GsonUtils {

    public static String convertObjectToStringJson(Object input) {
        Gson gson = new Gson();
        return gson.toJson(input);
    }

    public static <T> Object convertStringJsonToObject(T aclClass, String objString) {
        Gson gson = new Gson();
        return gson.fromJson(objString, aclClass.getClass());
    }

    public static Map convertStringJsonToMap(String objString) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(objString, new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );

        return map;
    }
}
