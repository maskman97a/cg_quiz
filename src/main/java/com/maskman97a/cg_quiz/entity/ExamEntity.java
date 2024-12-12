package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.TypeExamEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "exam")
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "UPDATE exam  SET is_deleted = 1 WHERE id = ?")
public class ExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "total_question")
    private int totalQuestion;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeExamEnum type;
    @Column(name = "total_time")
    private int totalTime;
    @Column(name = "graduate_mark")
    private int graduateMark;
    @Column(name = "max_person")
    private int maxPerson;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "is_deleted")
    private int isDeleted;
}
