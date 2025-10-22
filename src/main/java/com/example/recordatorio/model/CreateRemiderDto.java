package com.example.recordatorio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class CreateRemiderDto {

    @Email(message = "Debe ser un email valido")
    @NotBlank(message = "No puede estar bacio")
    private String sender;

    @Email(message = "Debe ser un email valido")
    @NotBlank(message = "No puede estar bacio")
    private String recipient;

    @NotBlank(message = "No puede estar bacio")
    private String subject;

    @NotBlank(message = "No puede estar bacio")
    private String body;

    @NotBlank(message = "No puede estar bacio")
    private LocalDateTime scheduled;


    @Enumerated(EnumType.STRING)
    private State state;
}
