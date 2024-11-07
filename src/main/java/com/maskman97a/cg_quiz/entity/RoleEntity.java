package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.RoleTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "UPDATE role SET is_deleted = 1 WHERE id = ?")
public class RoleEntity extends BaseEntity {
    @Column(name = "name", length = 500)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10)
    private RoleTypeEnum type;
    @Column(name = "code", length = 100)
    private String code;
}
