package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.AnswerValueEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
    private AnswerValueEnum value;
    private boolean isCorrect;
    private String description;
}
