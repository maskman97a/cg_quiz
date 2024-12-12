package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.ExamDetailEntity;
import com.maskman97a.cg_quiz.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamDetailRepository extends JpaRepository<ExamDetailEntity, Long> {
}
