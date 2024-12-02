package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultyEnum;
import com.maskman97a.cg_quiz.dto.enums.QuestionTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "question")
public class QuestionEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private QuestionTypeEnum type;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private QuestionDifficultyEnum difficulty;

    @ManyToOne
    @JoinColumn(name = "question_category_id", nullable = false)
    private QuestionCategoryEntity category;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerEntity> answers = new ArrayList<>();
}