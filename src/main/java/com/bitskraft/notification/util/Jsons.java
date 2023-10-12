package com.bitskraft.notification.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** created on: 10/2/23 created by: Anil Maharjan */
public class Jsons {
  private static final Gson gson = new GsonBuilder().serializeNulls().create();

  private Jsons() {}

  public static <T> String toJsonObj(T obj) {
    return gson.toJson(obj);
  }

  public static <T> T fromJson(String jsonString, Class<T> clazz) {
    if (isInvalidJsonString(jsonString)) {
      throw new IllegalArgumentException("Invalid Json data.");
    }
    return gson.fromJson(jsonString, clazz);
  }

  public static boolean isInvalidJsonString(String jsonString) {
    boolean isValid = true;
    if (!Util.isBlankOrNull(jsonString)) {
      try {
        gson.fromJson(jsonString, Object.class);
      } catch (Exception ex) {
        isValid = false;
      }
    }
    return !isValid;
  }
}
