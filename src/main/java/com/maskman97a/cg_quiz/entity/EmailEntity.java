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
@Table(name = "email")
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "UPDATE email SET is_deleted = 1 WHERE id = ?")
public class EmailEntity extends BaseEntity {
    @Column(name = "status")
    private MailStatus status;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "mail_title")
    private String mailTitle;
    @Column(name = "mail_body")
    private byte[] mailBody;
    @Column(name = "send_date")
    private LocalDateTime sendDate;
    @Column(name = "retry")
    private int retry;

}
