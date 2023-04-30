package com.example.services;

import com.example.models.Teacher;
import com.example.repository.TeachersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TeachersService {
    private final TeachersRepository teachersRepository;

    public TeachersService(TeachersRepository teachersRepository) {
        this.teachersRepository = teachersRepository;
    }

    public List<Teacher> getTeachersByParams(Map<String, Object> params) {
        UUID id = null;
        String firstname = (String) params.get("firstname");
        String lastname = (String) params.get("lastname");
        String email = (String) params.get("email");
        String username = (String) params.get("username");
        String office = (String) params.get("office");
        String title = (String) params.get("title");


        if (params.containsKey("id")) {
            if (!params.get("id").equals("")) {
                id = (UUID) params.get("id");
            }
        }

        return teachersRepository.findTeachersByParams(id, firstname, lastname, email, username, office, title);
    }

    public void saveTeacher(Teacher teacher) {
        teachersRepository.save(teacher);
    }
    @Transactional
    public void updateTeacher(UUID id, Teacher teacher) {
        // TODO : update the courses, it implies another table that makes connection between teacher and subjects
        teachersRepository.updateTeacher(id, teacher.getFirstname(), teacher.getLastname(), teacher.getEmail(), teacher.getUsername(), teacher.getOffice(), teacher.getTitle());
    }

}
