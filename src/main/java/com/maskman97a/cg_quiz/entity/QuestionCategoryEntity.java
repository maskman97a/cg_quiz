package com.maskman97a.cg_quiz.entity;

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
@Table(name = "question_category")
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCategoryEntity extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
