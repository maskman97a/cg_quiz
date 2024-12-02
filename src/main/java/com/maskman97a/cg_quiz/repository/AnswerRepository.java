package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    List<AnswerEntity> findByQuestionId(Long questionId);
}
