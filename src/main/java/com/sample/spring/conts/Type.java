package com.sample.spring.conts;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Type<T> {
    @JsonValue
    T getValue();

    String getDescription();
}

