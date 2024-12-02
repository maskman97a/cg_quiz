package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    Page<QuestionEntity> findAll(Pageable pageable);
    List<QuestionEntity> findByTitleContaining(String title);
}
