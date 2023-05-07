package com.example.services;

import com.example.models.Student;
import com.example.repository.StudentsRepository;
import jakarta.transaction.Transactional;
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

        if (params.containsKey("id") && (!params.get("id").equals(""))) {
                id = (UUID) params.get("id");

        }

        if (params.containsKey("year") && (!params.get("year").equals(""))) {
                year = Integer.parseInt((String) params.get("year"));

        }

        if (params.containsKey("semester") && (!params.get("semester").equals(""))) {
                semester = Integer.parseInt((String) params.get("semester"));

        }

        return studentsRepository.findStudentsByParams(id, firstname, lastname, email, username, year, semester, registrationNumber);
    }

    public void saveStudent(Student student) {
        studentsRepository.save(student);
    }
    @Transactional
    public void updateStudent(UUID id, Student student) {
        // TODO : update the courses, it implies another table that makes connection between teacher and subjects
        studentsRepository.updateStudent(id, student.getFirstname(), student.getLastname(), student.getEmail(), student.getUsername(), student.getYear(), student.getSemester(), student.getRegistrationNumber());
    }
}
