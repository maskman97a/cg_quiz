package com.maskman97a.cg_quiz.repository;

import com.maskman97a.cg_quiz.dto.enums.MailStatus;
import com.maskman97a.cg_quiz.entity.EmailEntity;
import com.maskman97a.cg_quiz.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> getOtpByOtpKeyAndFunction(String otpKey, String function);
}
