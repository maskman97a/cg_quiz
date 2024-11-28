package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.ExamResultAnswerEntity;
import com.maskman97a.cg_quiz.entity.ExamResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultAnswerRepository extends JpaRepository<ExamResultAnswerEntity, Long> {
    @Query("""
            SELECT ea
             FROM ExamResultAnswerEntity ea
             INNER JOIN ExamResultQuestionEntity eq ON ea.examResultQuestionId = eq.id
             INNER JOIN ExamResultEntity er ON eq.examResultId = er.id
             WHERE er.id = :examResultId
            """
    )
    List<ExamResultAnswerEntity> findByExamResultId(Long examResultId);

    List<ExamResultAnswerEntity> findByExamResultQuestionId(Long examResultQuestionId);

    @Query("""
            SELECT ea
            FROM ExamResultAnswerEntity ea
            INNER JOIN ExamResultQuestionEntity eq ON ea.examResultQuestionId = eq.id
            INNER JOIN ExamResultEntity er ON eq.examResultId = er.id
            INNER JOIN ExamEntity e on er.examId = e.id
            INNER JOIN ExamDetailEntity ed on e.id = ed.examId
            WHERE e.id = :examId
            AND ed.index = :questionNo
            """)
    List<ExamResultAnswerEntity> findByExamIdAndQuestionNo(Long examId, Integer questionNo);
}
