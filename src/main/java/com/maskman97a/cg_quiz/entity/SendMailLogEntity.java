package com.maskman97a.cg_quiz.entity;

import com.maskman97a.cg_quiz.dto.enums.SendMailStatus;
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
@Table(name = "send_mail_log")
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "UPDATE send_mail_log SET is_deleted = 1 WHERE id = ?")
public class SendMailLogEntity extends BaseEntity {
    @Column(name = "email_id")
    private Long emailId;
    @Column(name = "send_status")
    private SendMailStatus sendStatus;
    @Column(name = "current_retry")
    private Integer currentRetry;
}
