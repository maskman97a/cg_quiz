package com.maskman97a.cg_quiz.dto.enums;

public enum ExamTypeEnum {
    TEST("Test"),
    TRIAL("Trial");
    private final String name;

    ExamTypeEnum(String name) {
        this.name = name;
    }
}
