package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultyEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionTypeEnum;
import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class QuestionDTO {
    private String title;
    private QuestionTypeEnum type;
    private QuestionDifficultyEnum difficulty;
    private QuestionCategoryEntity categoryId;
    private List<AnswerDTO> answers;
}
