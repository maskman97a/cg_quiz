package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.dto.enums.RoleTypeEnum;
import com.maskman97a.cg_quiz.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {


    Optional<RoleEntity> findByType(RoleTypeEnum type);
}
