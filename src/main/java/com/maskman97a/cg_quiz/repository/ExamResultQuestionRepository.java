
package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.ExamResultEntity;
import com.maskman97a.cg_quiz.entity.ExamResultQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultQuestionRepository extends JpaRepository<ExamResultQuestionEntity, Long> {
    Optional<ExamResultQuestionEntity> getByExamResultIdAndQuestionId(Long examResultId, Long questionId);

    List<ExamResultQuestionEntity> findByExamResultId(Long examResultId);
}
