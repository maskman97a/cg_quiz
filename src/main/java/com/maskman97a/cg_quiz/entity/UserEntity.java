package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "UPDATE users SET is_deleted = 1 WHERE id = ?")
public class UserEntity extends BaseEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "user_type")
    private UserTypeEnum userType;
    @Column(nullable = false, name = "email")
    private String email;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "createdAt")
    private LocalDate createdAt;
}
