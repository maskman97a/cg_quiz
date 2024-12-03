package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.ExamResultEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResultEntity, Long> {
    Optional<ExamResultEntity> getByExamId(Long examId);

    List<ExamResultEntity> findAllByUserId(Long userId);

    Page<ExamResultEntity> findByUserIdOrderBySubmitTimeDesc(Long userId, Pageable pageable);
}
