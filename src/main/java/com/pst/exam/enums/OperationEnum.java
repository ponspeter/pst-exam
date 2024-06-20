package com.pst.exam.enums;

public enum OperationEnum {

    EQUAL("="),
    NOT_EQUAL("!="),
    LIKE("%"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUAL_TO("gte"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUAL_TO("lte"),
    BETWEEN("bet");

    private final String name;

    OperationEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
