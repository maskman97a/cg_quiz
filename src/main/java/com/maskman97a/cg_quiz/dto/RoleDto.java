package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.RoleTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
    private String type;

    public RoleDto(RoleTypeEnum roleTypeEnum) {
        this.type = roleTypeEnum.name();
    }
}
