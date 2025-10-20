package com.example.recordatorio.controller;

import com.example.recordatorio.model.CreateRemiderDto;
import com.example.recordatorio.model.EmailModel;
import com.example.recordatorio.model.State;
import com.example.recordatorio.repository.EmailRepository;
import com.example.recordatorio.service.EmailService;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;
    private final EmailRepository emailRepository;

    public EmailController(EmailService emailService, EmailRepository emailRepository) {
        this.emailService = emailService;
        this.emailRepository = emailRepository;
    }

    @PostMapping("/schedule")
    public ResponseEntity<EmailModel> scheduleEmail(@RequestBody CreateRemiderDto emailModel) {
        EmailModel email = new EmailModel();
        email.setSender("postmaster@sandbox6586a78150be4337af4fb7896d8b4aa3.mailgun.org");
        email.setRecipient(emailModel.getRecipient());
        email.setSubject(emailModel.getSubject());
        email.setBody(emailModel.getBody());
        email.setScheduledAt(emailModel.getScheduled());
        email.setState(State.pending);

        emailRepository.save(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(email);
    }
}
