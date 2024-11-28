package com.maskman97a.cg_quiz.dto;

import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionTypeEnum;
import com.maskman97a.cg_quiz.entity.AnswerEntity;
import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionInfoDto {
    private String title;

    private QuestionTypeEnum type;

    private QuestionDifficultEnum difficult;

    private QuestionCategoryEntity category;

    private List<AnswerInfoDto> answerList;
}
