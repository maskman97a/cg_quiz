package com.maskman97a.cg_quiz.dto.enums;

public enum QuestionDifficultyEnum {
    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard");

    private final String name;

    QuestionDifficultyEnum(String name) {
        this.name = name;
    }
}
