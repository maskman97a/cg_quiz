package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository {
    Page<StudentEntity> findAll(Pageable pageable);

    Page<StudentEntity> findByNameContaining(String name, Pageable pageable);
}
