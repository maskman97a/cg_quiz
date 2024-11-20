package com.maskman97a.cg_quiz.dto.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {
    TEACHER("Giao viên"), STUDENT("Học sinh"), ADMIN("Quản trị viên");
    private final String userType;

    UserTypeEnum(String userType) {
        this.userType = userType;
    }
}
