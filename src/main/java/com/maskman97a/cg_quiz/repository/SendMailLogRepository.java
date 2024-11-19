package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.dto.enums.MailStatus;
import com.maskman97a.cg_quiz.entity.EmailEntity;
import com.maskman97a.cg_quiz.entity.SendMailLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SendMailLogRepository extends JpaRepository<SendMailLogEntity, Long> {

}
