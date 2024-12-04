package com.maskman97a.cg_quiz.dto.enums;

public enum QuestionTypeEnum {
    SINGLE_SELECT("Single Select", "Đơn"),
    MULTI_SELECT("Multi Select", "Chọn đáp án"),
    TRUE_FALSE("True/False", "Đúng/Sai");

    private final String displayName;
    private final String displayNameVie;

    QuestionTypeEnum(String displayName, String displayNameVie) {
        this.displayName = displayName;
        this.displayNameVie = displayNameVie;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayNameVie() {
        return displayNameVie;
    }
}
