package com.maskman97a.cg_quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "teachers")
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "UPDATE teachers SET is_deleted = 1 WHERE id = ?")
public class TeacherEntity extends BaseEntity {
    @Id
    @Column( name = "email")
    private String email;
    @Column( name = "name_teacher")
    private String name;
    @Column( name = "created_At")
    private LocalDateTime createdAt;
}
