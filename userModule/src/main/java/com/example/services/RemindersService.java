package com.example.services;

import com.example.models.Reminder;
import com.example.repository.RemindersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RemindersService {
    private final RemindersRepository remindersRepository;

    public RemindersService(RemindersRepository remindersRepository) {
        this.remindersRepository = remindersRepository;
    }

    public List<Reminder> getRemindersByParams(Map<String, Object> params) {
        String creatorUsername = (String)params.get("creatorUsername");
        String title = (String) params.get("title");

        List<Reminder> reminders = remindersRepository.findRemindersByParams(creatorUsername, title);
        return reminders;
    }

    public void saveReminder(Reminder reminder) {
        remindersRepository.save(reminder);
    }
}

