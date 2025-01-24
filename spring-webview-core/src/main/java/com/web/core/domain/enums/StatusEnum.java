package com.web.core.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum StatusEnum {
    ACTIVE("ACT"),
    INACTIVE("INA"),
    TERMINATED("TER"),
    PENDING("PEN"),
    APPROVAL("APP"),
    REJECT("REJ"),
    COMPLETE("COM"),
    SUCCESS("SUC"),
    FAILED("FAI");

    private final String value;

    public static StatusEnum of(String value) {
        return Stream.of(StatusEnum.values())
            .filter(status -> StringUtils.equalsIgnoreCase(status.getValue(), value))
            .findFirst()
            .orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
