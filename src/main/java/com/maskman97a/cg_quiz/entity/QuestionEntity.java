package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "question")
public class QuestionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    public void setType(QuestionTypeEnum questionTypeEnum) {
    }

    public void setDifficult(QuestionDifficultEnum questionDifficultEnum) {
    }

    public void setQuestionCategoryId(Long id) {
    }

    public char[] getType() {
        return null;
    }

    public char[] getDifficult() {
        return null;
    }

    public Long getQuestionCategoryId() {
        return null;
    }
}
