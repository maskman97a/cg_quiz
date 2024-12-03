package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import com.maskman97a.cg_quiz.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategoryEntity, Long> {
    boolean existsByName(String name);
}
