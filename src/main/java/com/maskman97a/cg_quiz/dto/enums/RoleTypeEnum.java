package com.maskman97a.cg_quiz.dto.enums;

import lombok.Getter;

@Getter
public enum RoleTypeEnum {
    ADMIN("Quản lý"), USER("Người dùng"), DEFAULT("Mặc định");
    private final String name;

    RoleTypeEnum(String name) {
        this.name = name;
    }
}
