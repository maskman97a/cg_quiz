package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    Page<QuestionEntity> findByTitleContainingAndDifficulty(String title, String difficulty, Pageable pageable);
    Page<QuestionEntity> findByTitleContaining(String title, Pageable pageable);
}
