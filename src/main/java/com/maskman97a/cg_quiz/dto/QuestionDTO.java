package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.DifficultEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionTypeEnum;
import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDTO {
    private Long id;
    private String title;
    private QuestionTypeEnum type;
    private QuestionDifficultEnum difficulty;
    private Long categoryId;
    private List<AnswerDTO> answers;


    private QuestionDifficultEnum difficult;
    private Long questionCategoryId;
    private QuestionCategoryDTO category;
}
