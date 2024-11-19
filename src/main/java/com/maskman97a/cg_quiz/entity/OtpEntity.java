package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.MailStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "otp")
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "UPDATE otp SET is_deleted = 1 WHERE id = ?")
public class OtpEntity extends BaseEntity {
    @Column(name = "otp")
    private String otp;
    @Column(name = "reference_id")
    private Long referenceId;
    @Column(name = "function")
    private String function;
    @Column(name = "otpKey")
    private String otpKey;
    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

}
