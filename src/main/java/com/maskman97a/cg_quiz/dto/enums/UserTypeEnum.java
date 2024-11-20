package com.maskman97a.cg_quiz.dto.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {
    ADMIN("Quản lý"),
    STUDENT("Học Sinh"),
    TEACHER("Giáo Viên");

    private final String mean;

    UserTypeEnum(String mean) {
        this.mean = mean;
    }
}
