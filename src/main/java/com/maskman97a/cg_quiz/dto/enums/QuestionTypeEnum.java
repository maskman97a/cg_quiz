package com.maskman97a.cg_quiz.dto.enums;

public enum QuestionTypeEnum {
    SINGLE_SELECT("Single"),
    MULTI_SELECT ("Multi"),
    TRUE_FALSE("True False");

    private final String name;

    QuestionTypeEnum(String name) {
        this.name = name;
    }
}
