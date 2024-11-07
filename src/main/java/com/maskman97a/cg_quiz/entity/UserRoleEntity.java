package com.maskman97a.cg_quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@Table(name = "user_role")
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")

@SQLDelete(sql = "UPDATE user_role SET is_deleted = 1 WHERE id = ?")
public class UserRoleEntity extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "role_id")
    private Long roleId;
}
