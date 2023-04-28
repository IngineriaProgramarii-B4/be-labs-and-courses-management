package com.example.services;

import com.example.models.Student;
import com.example.repository.StudentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StudentsService {
    private final StudentsRepository studentsRepository;

    public StudentsService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public List<Student> getStudentsByParams(Map<String, Object> params) {
        UUID id = null;
        String firstname = (String) params.get("firstname");
        String lastname = (String) params.get("lastname");
        String email = (String) params.get("email");
        String username = (String) params.get("username");
        Integer year = 0;
        Integer semester = 0;
        String registrationNumber = (String) params.get("registrationNumber");

        if (params.containsKey("id")) {
            if (!params.get("id").equals("")) {
                id = UUID.fromString((String) params.get("id"));
            }
        }

        if (params.containsKey("year")) {
            if (!params.get("year").equals("")) {
                year = Integer.parseInt((String) params.get("year"));
            }
        }

        if (params.containsKey("semester")) {
            if (!params.get("semester").equals("")) {
                semester = Integer.parseInt((String) params.get("semester"));
            }
        }

        List<Student> students = studentsRepository.findStudentsByParams(id, firstname, lastname, email, username, year, semester, registrationNumber);

        return students;
    }

    public void saveStudent(Student student) {
        studentsRepository.save(student);
    }
}
