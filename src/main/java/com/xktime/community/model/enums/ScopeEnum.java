package com.xktime.community.model.enums;

public enum ScopeEnum {
    USER("user"),
    REPO("repo"),
    ;
    private String value;
    ScopeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
