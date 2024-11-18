package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);

    Page<Student> findByNameContaining(String name, Pageable pageable);
}
