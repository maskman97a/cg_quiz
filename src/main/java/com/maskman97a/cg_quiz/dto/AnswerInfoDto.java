package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.AnswerValueEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerInfoDto {
    private Long id;

    private AnswerValueEnum value;

    private String description;

    private Long questionId;

    private boolean selected;
}
