package com.maskman97a.cg_quiz.service;


import com.maskman97a.cg_quiz.dto.enums.MailStatus;
import com.maskman97a.cg_quiz.dto.enums.SendMailStatus;
import com.maskman97a.cg_quiz.entity.EmailEntity;
import com.maskman97a.cg_quiz.entity.SendMailLogEntity;
import com.maskman97a.cg_quiz.repository.EmailRepository;
import com.maskman97a.cg_quiz.repository.SendMailLogRepository;
import com.maskman97a.cg_quiz.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final Session session;
    private final String email;
    private final EmailRepository emailRepository;
    private final SendMailLogRepository sendMailLogRepository;

    private final Integer MAX_RETRY = 5;


    public void sendEmailFromDB() {
        List<SendMailLogEntity> sendMailLogEntityList = new ArrayList<>();
        List<EmailEntity> emailList = emailRepository.findNeedToSend(MAX_RETRY, MailStatus.NEW);
        for (EmailEntity emailEntity : emailList) {
            SendMailLogEntity sendMailLogEntity = new SendMailLogEntity();
            boolean success = EmailUtils.sendEmail(session, email, emailEntity.getReceiver(), emailEntity.getMailTitle(), new String(Base64.getDecoder().decode(emailEntity.getMailBody())));
            int currentRetry = emailEntity.getRetry() + 1;
            emailEntity.setRetry(currentRetry);
            if (success) {
                sendMailLogEntity.setSendStatus(SendMailStatus.SUCCESS);
                emailEntity.setStatus(MailStatus.SENT);
            } else {
                sendMailLogEntity.setSendStatus(SendMailStatus.FAILED);
                emailEntity.setStatus(MailStatus.FAILED);
            }
            sendMailLogEntityList.add(sendMailLogEntity);
        }
        sendMailLogRepository.saveAll(sendMailLogEntityList);
        emailRepository.saveAll(emailList);
    }
}
