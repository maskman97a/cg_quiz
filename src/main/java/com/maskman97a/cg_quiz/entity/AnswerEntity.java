package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.AnswerValueEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "answer")
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "value", nullable = false)
    private AnswerValueEnum value;

    @Column(name = "correct", nullable = false)
    private Boolean correct;

    @Column(name = "description")
    private String description;

    @Column(name = "question_id", nullable = false)
    private Long questionId;
}
