package com.maskman97a.cg_quiz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${email.user}")
    private String mailUserName;
    @Value("${email.password}")
    private String mailPassword;
    @Value("${email.smtp.host}")
    private String smtpHost;
    @Value("${email.smtp.port}")
    private String smtpPort;
    @Value("${email.smtp.auth}")
    private String smtpAuth;
    @Value("${email.smtp.starttls.enable}")
    private String smtpStartTLS;


    @Bean
    public String getEmail() {
        return mailUserName;
    }

    @Bean
    public Session getSession() {
        // Sender's email ID and password need to be mentioned
        final String from = mailUserName;
        final String password = mailPassword;
        // Setting up configurations for the email connection to the Google SMTP server using TLS
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.starttls.enable", smtpStartTLS);

        // Create a session with an authenticator
        return javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
    }
}
