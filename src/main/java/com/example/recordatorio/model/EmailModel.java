package com.example.recordatorio.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "email_model")
public class EmailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "sender",nullable = false)
    private String sender;

    @Column(name = "recipient",nullable = false)
    private String recipient;

    @Column(name = "subject",nullable = false)
    private String subject;

    @Column(name = "body",nullable = false)
    private String body;

    @Column(name = "scheduled_at",nullable = false)
    private LocalDateTime scheduledAt;

    @Column(name = "send",nullable = true)
    @Enumerated(EnumType.STRING)
    private State state;

}
