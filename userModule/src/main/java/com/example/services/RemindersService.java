package com.example.services;

import com.example.models.Reminder;
import com.example.repository.RemindersRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RemindersService {
    private final RemindersRepository remindersRepository;

    public RemindersService(RemindersRepository remindersRepository) {
        this.remindersRepository = remindersRepository;
    }

    public List<Reminder> getRemindersByParams(Map<String, Object> params) {
        String creatorUsername = (String)params.get("creatorUsername");
        UUID id = (UUID) params.get("id");
        return remindersRepository.findRemindersByParams(creatorUsername, id);
    }

    public void saveReminder(Reminder reminder) {
        remindersRepository.save(reminder);
    }

    public void removeReminder(UUID uuid) {
        remindersRepository.deleteById(uuid);
    }

    @Transactional
    public void updateReminder(UUID id, Reminder reminder) {
        remindersRepository.updateReminder(id, reminder.getCreatorId(), reminder.getCreatorUsername(), reminder.getDueDateTime(), reminder.getTitle(), reminder.getDescription());
    }
}

