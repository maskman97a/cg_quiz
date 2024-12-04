package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.dto.enums.QuestionDifficultEnum;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    Page<QuestionEntity> findByTitleContainingAndDifficult(String title, String difficult, Pageable pageable);

    Page<QuestionEntity> findByTitleContaining(String title, Pageable pageable);

    @Query("""
            SELECT q
            FROM QuestionEntity q
            INNER JOIN ExamDetailEntity ed ON ed.questionId = q.id
            INNER JOIN ExamEntity e ON e.id = ed.examId
            WHERE e.id = :examId
            AND ed.index = :questionNo
            """)
    QuestionEntity findByExamIdAndQuestionNo(Long examId, Integer questionNo);

    @Query("""
            SELECT q
            FROM QuestionEntity q
            INNER JOIN ExamDetailEntity ed ON ed.questionId = q.id
            INNER JOIN ExamEntity e ON e.id = ed.examId
            WHERE e.id = :examId
            """)
    List<QuestionEntity> findByExamId(Long examId);

    Page<QuestionEntity> findAll(Pageable pageable);

    @Query("""
            SELECT q
            FROM QuestionEntity q
            WHERE q.title LIKE %:keyword%
            AND (:difficult IS NULL OR q.difficult = :difficult)
            """)
    List<QuestionEntity> findByTitleContaining(String keyword, QuestionDifficultEnum difficult);
}
