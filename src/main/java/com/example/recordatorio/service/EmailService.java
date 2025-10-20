package com.example.recordatorio.service;

import com.example.recordatorio.model.EmailModel;
import com.example.recordatorio.model.State;
import com.example.recordatorio.repository.EmailRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(EmailRepository emailRepository, JavaMailSender mailSender) {
        this.emailRepository = emailRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void sendScheduledEmails() {
        Date now = new Date();


        List<EmailModel> pendingEmails = emailRepository
                .findByStateAndScheduledAtBeforeForUpdate(State.pending, now);

        for (EmailModel email : pendingEmails) {
            try {
                sendEmail(email);
                email.setState(State.sent);
                emailRepository.save(email);
                logger.info("Email enviado a {}", email.getRecipient());
            } catch (Exception e) {
                logger.error("Error al enviar email a {}: {}", email.getRecipient(), e.getMessage());
                emailRepository.save(email);
            }
        }
    }

    private void sendEmail(EmailModel email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("postmaster@sandbox6586a78150be4337af4fb7896d8b4aa3.mailgun.org");
        message.setTo(email.getRecipient());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        mailSender.send(message);
    }
}