package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.QuestionCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategoryEntity, Long> {
    boolean existsByName(String name);

    List<QuestionCategoryEntity> findByNameContainingOrDescriptionContaining(String name, String description);
}


