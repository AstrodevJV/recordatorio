package com.example.recordatorio.repository;

import com.example.recordatorio.model.EmailModel;
import com.example.recordatorio.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Long> {
    List<EmailModel> findByStateAndScheduledAtBefore(State state, Date scheduledAt);}
