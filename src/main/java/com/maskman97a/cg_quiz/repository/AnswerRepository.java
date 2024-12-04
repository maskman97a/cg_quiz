package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    List<AnswerEntity> findByQuestionIdOrderByValue(Long questionId);

    List<AnswerEntity> findByQuestionIdAndCorrect(Long questionId, boolean correct);

    @Query("""
            SELECT a
            FROM AnswerEntity a
            WHERE a.questionId IN :questionIds
            AND a.correct = true
            """)
    List<AnswerEntity> findAllRightAnswerForQuestion(List<Long> questionIds);

    List<AnswerEntity> findByQuestionId(Long questionId);
}
