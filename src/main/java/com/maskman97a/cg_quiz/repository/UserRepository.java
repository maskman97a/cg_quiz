package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameIgnoreCase(String username);
}
