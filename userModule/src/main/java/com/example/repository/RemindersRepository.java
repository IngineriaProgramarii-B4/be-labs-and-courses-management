package com.example.repository;

import com.example.models.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RemindersRepository extends JpaRepository<Reminder, UUID> {

    @Query("select a from Reminder a where (?1 is null or a.creatorUsername=?1) and (cast(?2 as uuid) is null or a.reminderId = ?2)")
    List<Reminder> findRemindersByParams(String creatorUsername, UUID id);
}