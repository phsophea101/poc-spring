package com.sample.spring.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil extends StringUtils {

    public static String buildURL(String url) {
        if (StringUtils.isNotEmpty(url))
            return isValidURL(url) ? url : String.format("%s://%s", "http", url);
        throw new IllegalArgumentException("url cannot be empty");
    }

    public static boolean isValidURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String extractBaseUrl(URI uri) {
        String baseUrl = uri.getScheme() + "://" + uri.getHost();
        int port = uri.getPort();
        if (port > 0) baseUrl += ":" + port;
        return baseUrl;
    }

    public static String extractPlaceHolder(String placeHolder) {
        if (StringUtils.isEmpty(placeHolder)) return placeHolder;
        String regex = "\\$\\{(.*?)}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(placeHolder);
        return matcher.find() ? matcher.group(1) : placeHolder;
    }

    public static String collectionToCommaDelimitedString(@Nullable Collection<?> coll) {
        return org.springframework.util.StringUtils.collectionToDelimitedString(coll, ",");
    }

    public static Set<String> commaDelimitedListToSet(@Nullable String str) {
        return org.springframework.util.StringUtils.commaDelimitedListToSet(str);
    }

    public static String substringLast(String string) {
        if (isEmpty(string)) return string;
        return string.substring(0, string.length() - 1);
    }

    public static String ifElse(String stringIf, String stringElse) {
        return StringUtils.isEmpty(stringIf) ? stringElse : stringIf;
    }

    public static boolean isNotEmpty(Object string) {
        return isNotEmpty(String.valueOf(string));
    }

    public static String camelCaseToLowerHyphen(String string) {
        String regex = "(?=[A-Z][a-z])";
        String subst = "-";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.replaceAll(subst).toLowerCase().trim();
    }

    public static String snakeCaseToCamelCase(String string) {
        if (isEmpty(string)) return string;
        StringBuilder sb = new StringBuilder(string);
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '_') {
                sb.deleteCharAt(i);
                sb.replace(i, i + 1, String.valueOf(Character.toUpperCase(sb.charAt(i))));
            }
        }
        return sb.toString();
    }

    public static String mapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Map.Entry<String, String> entry : map.entrySet())
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        String message = sb.substring(0, sb.length() - 1);
        return message + "]";
    }

    public static String mapToQueryParam(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=").append(entry.getValue());
        }
        return sb.toString().trim();
    }

    public static String[] array(String... values) {
        List<String> list = new ArrayList<>();
        for (String value : values)
            if (StringUtils.isNotEmpty(value))
                list.add(value);
        return list.toArray(new String[list.size()]);
    }

    public static String oneLineTrim(String string) {
        return StringUtils.isEmpty(string) ? "" : string.replaceAll("[\\r\\n]", "").trim();
    }

    public static String oneLineNoSpace(String string) {
        return StringUtils.isEmpty(string) ? "" : string.replaceAll("\\r|\\n|\\t|\\s", "").trim();
    }

    public static String oneLine(String string) {
        return StringUtils.isEmpty(string) ? "" : string.replaceAll("[\\r\\n\\t]", "").trim();
    }

    public static String oneLineXml(String string) throws IOException {
        if (string == null || string.isEmpty())
            return "";
        BufferedReader br = new BufferedReader(new StringReader(string));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line.trim());
        return sb.toString().trim();
    }

    public static String camelCaseToSnakeCase(String string) {
        if (isEmpty(string)) throw new RuntimeException("String value is required");
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return string.replaceAll(regex, replacement).toLowerCase().trim();
    }

    public static String random(int length) {
        return random(length, "0123456789");
    }

    public static String random(int tokenLength, String tokenFormat) {
        Assert.isTrue(0 < tokenLength, "Token length can not less then zero");
        if (StringUtil.isEmpty(tokenFormat)) throw new IllegalArgumentException("Token format is required");
        Random random = new SecureRandom();
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < tokenLength; i++)
            msg.append(tokenFormat.charAt(random.nextInt(tokenFormat.length())));
        return msg.toString().trim();
    }
}
