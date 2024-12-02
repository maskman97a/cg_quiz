package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.AnswerValueEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "answer")
public class AnswerEntity extends  BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "value", nullable = false)
    private AnswerValueEnum value;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;
}
