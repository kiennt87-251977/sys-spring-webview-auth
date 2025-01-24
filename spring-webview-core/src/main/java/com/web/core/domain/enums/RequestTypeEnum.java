package com.web.core.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum RequestTypeEnum {
    CREATE("CRE"),
    OFF("OFF");

    private final String value;

    public static RequestTypeEnum of(String value) {
        return Stream.of(RequestTypeEnum.values())
            .filter(requestType -> StringUtils.equalsIgnoreCase(requestType.getValue(), value))
            .findFirst()
            .orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
