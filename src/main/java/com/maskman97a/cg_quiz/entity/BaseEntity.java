package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.DeleteEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@FieldNameConstants
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
    @Column(name = "created_by")
    protected String createdBy;
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
    @Column(name = "updated_by")
    protected String updatedBy;
    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1)")
    protected DeleteEnum isDeleted;

    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isDeleted = DeleteEnum.NO;
    }

    @PrePersist
    protected void onCreate() {
        isDeleted = DeleteEnum.NO;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "system";
    }
}
