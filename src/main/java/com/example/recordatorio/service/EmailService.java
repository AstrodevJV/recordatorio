package com.example.recordatorio.service;

import com.example.recordatorio.model.EmailModel;
import com.example.recordatorio.model.State;
import com.example.recordatorio.repository.EmailRepository;
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
    private final JavaMailSender javaMailSender;
    private final MailSender mailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender javaMailSender, MailSender mailSender) {
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
        this.mailSender = mailSender;
    }

    @Scheduled(fixedRate = 60000) // cada 60 segundos
    public void sendScheduledEmails() {
        Date now = new Date();
        List<EmailModel> pendingEmails = emailRepository.findByStateAndScheduledAtBefore(State.pending, now);

        for (EmailModel email : pendingEmails) {
            try {
                sendEmail(email);
                email.setState(State.sent);
            } catch (Exception e) {
                email.setState(State.pending);
            }
            emailRepository.save(email);
        }
    }


    private void sendEmail(EmailModel email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email.getSender());
        message.setTo(email.getRecipient());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        mailSender.send(message);
    }

}
