package com.household.finance.common.service;

import com.household.finance.user.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    private static final String EMAIL_ORIGIN = "caixinha.app@email.com";
    private static final String SENDER_NAME = "Caixinha App";

    public static final String URL_SITE = "http://localhost:8080";

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    private void sendEmail(String userEmail, String subject, String text) {
        MimeMessage message = this.emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(EMAIL_ORIGIN, SENDER_NAME);
            helper.setTo(userEmail);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch(MessagingException | UnsupportedEncodingException e){
            throw new RuntimeException("Email send error");
        }

        this.emailSender.send(message);
    }

    public void sendVerificationEmail(User user) {
        String subject = "Confirmar email";

        String text = generateEmailContent("Olá [[name]], Bem vindo(a) ao Caixinha App!<br>"
                + "Use esse código para ativar sua conta: <br>"
                + "[[codigo]] <br>"
                + "Obrigado,<br>"
                + "Caixinha App :).", user.getName(), String.valueOf(user.getConfirmationCode()));

        this.sendEmail(user.getEmail(), subject, text);
    }

    private String generateEmailContent(String template, String name, String url) {
        return template.replace("[[name]]", name).replace("[[URL]]", url);
    }
}
