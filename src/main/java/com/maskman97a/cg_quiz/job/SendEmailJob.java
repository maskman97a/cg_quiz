package com.maskman97a.cg_quiz.job;

import com.maskman97a.cg_quiz.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@RequiredArgsConstructor
public class SendEmailJob {
    private final EmailService emailService;


    @Scheduled(cron = "${cron.send.email}")
    public void sendEmail() {
        emailService.sendEmailFromDB();
    }
}
