package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
}
