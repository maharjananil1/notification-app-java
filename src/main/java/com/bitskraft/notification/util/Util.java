package com.bitskraft.notification.util;

import java.util.Base64;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.bitskraft.notification.common.exceptionhandlers.exceptions.DataMissingException;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.InvalidException;

/** created on: 10/2/23 created by: Anil Maharjan */
public class Util {
  public static boolean isBlankOrNull(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if ((!Character.isWhitespace(str.charAt(i)))) {
        return false;
      }
    }
    return true;
  }

  public static String commaSeparatedAddress(Set<String> addresses) {
    StringBuilder builder = new StringBuilder();
    for (String address : addresses) {
      builder.append(address);
      builder.append(",");
    }
    return builder.toString();
  }

  public static String decodeString(String encodedString) {
    byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
    return new String(decodedBytes);
  }

  public static String encodeString(String normalString) {
    return Base64.getEncoder().encodeToString(normalString.getBytes());
  }

  public static String replaceField(String content, Map<String, String> replacementValues) {
    for (Map.Entry<String, String> value : replacementValues.entrySet()) {
      content = content.replace("{{" + value.getKey() + "}}", value.getValue());
    }
    return content;
  }

  public static void validateAllDynamicFieldsExists(String body, Map<String, String> data) {
    Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
    Matcher matcher = pattern.matcher(body);
    Stream<MatchResult> results = matcher.results();
    Set<String> fields = new HashSet<>();
    results.forEach(r -> fields.add(r.group(1)));
    for (String field : fields) {
      if (data.get(field) == null) throw new DataMissingException(field + " data is missing.");
    }
  }

  public static String commaSeparatedPhoneNumber(Set<String> to) {
    StringBuilder numbers = new StringBuilder();
    int count = 0;
    for (String s : to) {
      count++;
      numbers.append(s);
      if (count < to.size()) {
        numbers.append(",");
      }
    }
    return numbers.toString();
  }

  public static void validatePhoneNumbers(Set<String> to) {
    Pattern pattern = Pattern.compile("[0-9]{10}");
    to.forEach(
        t -> {
          Matcher matcher = pattern.matcher(t);
          if (!matcher.matches()) throw new InvalidException("Phone number invalid : " + t);
        });
  }

  public static void validatePhoneNumbersTwilio(Set<String> to) {
    Pattern pattern = Pattern.compile("[0-9\\+]{14}");
    to.forEach(
        t -> {
          Matcher matcher = pattern.matcher(t);
          if (!matcher.matches()) throw new InvalidException("Phone number invalid : " + t);
        });
  }
}
