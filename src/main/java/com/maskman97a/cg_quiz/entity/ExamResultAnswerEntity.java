package com.maskman97a.cg_quiz.entity;

import jakarta.persistence.Column;
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
@Table(name = "exam_result_answer")
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultAnswerEntity extends BaseEntity{
    @Column(name = "exam_result_question_id", nullable = false)
    private Long examResultQuestionId;

    @Column(name = "answer_id", nullable = false)
    private Long answerId;
}
