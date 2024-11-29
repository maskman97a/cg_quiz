package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.DeleteEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "exam_result")
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultEntity  extends BaseEntity{
    @Column(name = "exam_id", nullable = false)
    private Long examId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "mark", nullable = false)
    private Integer mark;

    @Column(name = "graduate")
    private Enum graduate;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @Column(name = "submit_time")
    private LocalDateTime submitTime;

}
