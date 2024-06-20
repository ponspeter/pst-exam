package com.pst.exam.enums;

public enum FieldEnum {
    LENGTH("length"),
    WEIGHT("weight"),
    VELOCITY("velocity"),
    COLOR("color");

    private final String name;
    FieldEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
