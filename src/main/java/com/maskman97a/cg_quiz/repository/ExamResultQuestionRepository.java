
package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.dto.response.ExamResultDetailDto;
import com.maskman97a.cg_quiz.entity.ExamResultQuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultQuestionRepository extends JpaRepository<ExamResultQuestionEntity, Long> {
    Optional<ExamResultQuestionEntity> getByExamResultIdAndQuestionId(Long examResultId, Long questionId);

    List<ExamResultQuestionEntity> findByExamResultId(Long examResultId);

    @Query(value = """
                SELECT 
                    q.title AS questionTitle,
                    a.description AS selectedAnswer,
                    GROUP_CONCAT(a2.description) AS correctAnswers,
                    IFNULL(a.correct,0) AS isCorrect
                FROM exam_result_question exq
                LEFT JOIN question q ON q.id = exq.question_id AND q.is_deleted = 0
                LEFT JOIN exam_result_answer era ON era.exam_result_question_id = exq.id AND era.is_deleted = 0
                LEFT JOIN answer a ON a.id = era.answer_id
                LEFT JOIN answer a2 ON a2.question_id = q.id AND a2.correct = 1
                WHERE exq.is_deleted = 0
                  AND exq.exam_result_id = :examResultId
                GROUP BY q.title, a.value, a.correct, a.description
                ORDER BY q.title, a2.value
            """, nativeQuery = true)
    Page<ExamResultDetailDto> findExamResultDetails(Pageable pageable, @Param("examResultId")Long userId);
}
