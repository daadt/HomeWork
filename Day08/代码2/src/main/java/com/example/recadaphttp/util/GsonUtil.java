package com.example.recadaphttp.util;

import com.google.gson.Gson;

public class GsonUtil {

    private static Gson gson;
    static {
        gson = new Gson();
    }

    public static <T> T str2Bean(String jsonStr, Class<T> tClass){
        if (gson != null)
            return gson.fromJson(jsonStr, tClass);
        return null;
    }
}
