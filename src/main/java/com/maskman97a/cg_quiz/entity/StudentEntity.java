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
@Table(name = "student")
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "UPDATE student SET is_deleted = 1 WHERE id = ?")
public class StudentEntity {
    @Id
    @Column(nullable = false, name = "email")
    private String email;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "createdAt")
    private LocalDateTime createdAt;
}
