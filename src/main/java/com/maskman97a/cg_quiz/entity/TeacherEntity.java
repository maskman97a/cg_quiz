package com.maskman97a.cg_quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "teacher")
@Getter
@Setter
public class TeacherEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
}
