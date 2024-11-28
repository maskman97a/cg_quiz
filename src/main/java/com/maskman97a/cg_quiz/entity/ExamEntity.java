package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.ExamTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "exam")
@AllArgsConstructor
@NoArgsConstructor
public class ExamEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "total_question", nullable = false)
    private Integer totalQuestion;

    @Column(name = "total_time", nullable = false)
    private Integer totalTime;

    @Column(name = "graduate_mark", nullable = false)
    private Double graduateMark;

    @Column(name = "max_person", nullable = false)
    private Integer maxPerson;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ExamTypeEnum type;
}
