package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
import com.maskman97a.cg_quiz.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailIgnoreCase(String username);

    List<UserEntity> findByUserTypeOrderByCreatedAtDesc(Pageable pageable, UserTypeEnum userTypeEnum);
    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findByNameContaining(String name, Pageable pageable);
}
