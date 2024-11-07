package com.maskman97a.cg_quiz.dto.enums;

import lombok.Getter;

@Getter
public enum DeleteEnum {
    YES(1),
    NO(0);

    int value;

    DeleteEnum(int value) {
        this.value = value;
    }
}
