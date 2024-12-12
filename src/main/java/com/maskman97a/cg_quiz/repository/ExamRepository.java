package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.ExamEntity;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {

    Page<ExamEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
