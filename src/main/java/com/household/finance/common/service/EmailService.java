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
        String subject = "Confirme seu cadastro";

        String text = generateEmailContent("""
                Olá, [[name]]
                <br><br>
                Recebemos uma solicitação para criar sua conta.
                <br><br>
                Para concluir o cadastro e confirmar seu endereço de e-mail, utilize o código de verificação abaixo:
                <br><br>
                 Código de confirmação: [[code]]
                <br><br>
                Digite este código na tela de confirmação para ativar sua conta.
                <br>
                Por motivos de segurança, o código é válido por tempo limitado.
                <br><br>
                Se você não solicitou este cadastro, pode desconsiderar este e-mail.
                <br><br>
                Atenciosamente,
                <br>
                Caixinha App""", user.getName(), String.valueOf(user.getConfirmationCode()));

        this.sendEmail(user.getEmail(), subject, text);
    }

    public void sendResetPasswordEmail(User user) {
        String subject = "Redefinição de senha";

        String text = generateEmailContent("""
                Olá, [[name]]
                <br><br>
                Recebemos uma solicitação para redefinir sua senha.
                <br><br>
                Para concluir o processo de redefinição, utilize o código de verificação abaixo:
                <br><br>
                 Código de confirmação: [[code]]
                <br><br>
                Digite este código na tela de redefinição para criar uma nova senha.
                <br>
                Por motivos de segurança, o código é válido por tempo limitado.
                <br><br>
                Se você não solicitou esta redefinição, pode desconsiderar este e-mail.
                <br><br>
                Atenciosamente,
                <br>
                Caixinha App""", user.getName(), String.valueOf(user.getConfirmationCode()));

        this.sendEmail(user.getEmail(), subject, text);
    }

    private String generateEmailContent(String template, String name, String code) {
        return template.replace("[[name]]", name).replace("[[code]]", code);
    }
}
