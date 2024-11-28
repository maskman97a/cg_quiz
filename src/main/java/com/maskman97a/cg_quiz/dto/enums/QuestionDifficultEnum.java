package com.maskman97a.cg_quiz.dto.enums;

public enum QuestionDifficultEnum {
    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard");

    private final String name;

    QuestionDifficultEnum(String name) {
        this.name = name;
    }
}
