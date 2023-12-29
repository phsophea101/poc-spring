package com.sample.spring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        int status = (int) map.get("status");
        String message = (String) map.get("error");
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        error.putIfAbsent("code", String.format("E0%s", status));
        error.putIfAbsent("message", message);
        error.putIfAbsent("description", message);
        response.putIfAbsent("status", String.valueOf(status));
        response.putIfAbsent("result", "Failed");
        response.putIfAbsent("error", error);
        response.putIfAbsent("data", null);
        return response;
    }
}