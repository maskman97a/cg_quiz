package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "UPDATE users SET is_deleted = 1 WHERE id = ?")
public class UserEntity extends BaseEntity {
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "fullName")
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserTypeEnum userType;
    @Column( name = "nameteacher")
    private String name;
    @Column( name = "createdAt")
    private LocalDateTime createdAt;

    public UserEntity(BaseEntityBuilder<?, ?> b, String email, String password, String fullName, UserTypeEnum userType, String name, LocalDateTime createdAt) {
        super(b);
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.userType = userType;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UserEntity(String email, String password, String fullName, UserTypeEnum userType, String name, LocalDateTime createdAt) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.userType = userType;
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
