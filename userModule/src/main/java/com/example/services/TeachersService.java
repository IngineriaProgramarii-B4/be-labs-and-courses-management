package com.example.services;

import com.example.models.Teacher;
import com.example.repository.TeachersRepository;
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
                id = UUID.fromString((String) params.get("id"));
            }
        }

        List<Teacher> teachers = teachersRepository.findTeachersByParams(id, firstname, lastname, email, username, office, title);

        return teachers;
    }

    public void saveTeacher(Teacher teacher) {
        teachersRepository.save(teacher);
    }
}
