package com.example.recordatorio.repository;

import com.example.recordatorio.model.EmailModel;
import com.example.recordatorio.model.State;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Long> {

    @Query("SELECT e FROM EmailModel e WHERE e.state = :state AND e.scheduledAt <= :now")
    List<EmailModel> findByStateAndScheduledAtBeforeForUpdate(@Param("state") State state,
                                                              @Param("now") Date now);

}
