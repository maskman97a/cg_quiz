package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.TeacherEntity;
import com.maskman97a.cg_quiz.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
//    Page<TeacherEntity> findAll(Pageable pageable);

    Page<TeacherEntity> findByNameContaining(String name, Pageable pageable);
}
