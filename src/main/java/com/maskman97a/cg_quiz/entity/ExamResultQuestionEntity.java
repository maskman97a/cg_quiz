package com.maskman97a.cg_quiz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "exam_result_question")
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultQuestionEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "exam_result_id", nullable = false)
    private ExamResultEntity examResult;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;
}
