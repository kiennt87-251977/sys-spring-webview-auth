package com.web.core.domain.enums;

public enum RoleEnum {
    SUPER_AGENT(7),
    SA_STAFF(19);

    private final int value;

    RoleEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
