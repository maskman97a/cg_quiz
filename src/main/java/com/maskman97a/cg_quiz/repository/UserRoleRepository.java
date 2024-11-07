package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.dto.RoleDto;
import com.maskman97a.cg_quiz.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<com.maskman97a.cg_quiz.entity.UserRoleEntity, Long> {
    @Query("""
            SELECT new com.maskman97a.cg_quiz.dto.RoleDto(r.type)
            FROM UserRoleEntity ur
             inner join RoleEntity r on ur.roleId = r.id
             WHERE ur.userId = :userId
            """)
    Set<RoleDto> getUserRole(Long userId);
}
