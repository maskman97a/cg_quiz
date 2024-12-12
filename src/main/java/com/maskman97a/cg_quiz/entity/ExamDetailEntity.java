package com.maskman97a.cg_quiz.entity;

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
@Table(name = "exam_detail")
public class ExamDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="exam_id", nullable = false)
    private ExamEntity examId;
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity questionId;

}
