package com.maskman97a.cg_quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "exam_detail")

public class ExamDetailEntity extends BaseEntity {
    @Column(name = "exam_id")
    private Long examId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "indx")
    private Integer index;
}
