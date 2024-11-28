package com.maskman97a.cg_quiz.dto.enums;

import lombok.Getter;

@Getter
public enum DifficultEnum {
    EASY("Dễ", 6, 3, 1),
    NORMAL("Trung bình", 3, 6, 1),
    HARD("Khó", 1, 3, 6);

    private final String description;
    private final int totalEasy;
    private final int totalNormal;
    private final int totalHard;

    DifficultEnum(String description, int totalEasy, int totalNormal, int totalHard) {
        this.description = description;
        this.totalEasy = totalEasy;
        this.totalNormal = totalNormal;
        this.totalHard = totalHard;
    }
}
