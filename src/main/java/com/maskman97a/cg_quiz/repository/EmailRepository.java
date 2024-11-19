package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.dto.enums.MailStatus;
import com.maskman97a.cg_quiz.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    @Query("""
            SELECT e 
            FROM EmailEntity e
            WHERE e.retry < :retry
            AND e.status = :status         
            """)
    List<EmailEntity> findNeedToSend(int retry, MailStatus status);
}
