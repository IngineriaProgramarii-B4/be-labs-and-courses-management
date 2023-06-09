package com.example.services;

import com.example.models.Grade;
import com.example.models.Student;
import com.example.repository.StudentsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        int year = 0;
        int semester = 0;
        String registrationNumber = (String) params.get("registrationNumber");

        final String idKey = "id";
        if (params.containsKey(idKey) && (!params.get(idKey).equals(""))) {
                id = (UUID) params.get(idKey);

        }
        final String yearKey = "year";
        if (params.containsKey(yearKey) && (!params.get(yearKey).equals(""))) {
                year = Integer.parseInt((String) params.get(yearKey));

        }
        final String semesterKey = "semester";
        if (params.containsKey(semesterKey) && (!params.get(semesterKey).equals(""))) {
            semester = Integer.parseInt((String) params.get(semesterKey));
        }

        return studentsRepository.findStudentsByParams(id, firstname, lastname, email, username, year, semester, registrationNumber);
    }

    public Student saveStudent(Student student) {
        if (student == null)
            return null;
        studentsRepository.save(student);
        return student;
    }

    @Transactional
    public Student deleteStudent(UUID id) {
        Student student = studentsRepository.findStudentById(id);
        if (student == null)
            return null;
        return student.setDeleted();
    }
    @Transactional
    public void updateStudent(UUID id, Student student) {
        // TODO : update the courses, it implies another table that makes connection between teacher and subjects
        studentsRepository.updateStudent(id, student.getFirstname(), student.getLastname(), student.getEmail(), student.getUsername(), student.getYear(), student.getSemester(), student.getRegistrationNumber());
    }

    // <-------------------------------- FROM CATALOG ----------------------------------> //

    // Q: te poti folosi de getStudentsByParams - A: nu, ca da o lista de studenti si n-am nevoie de o lista daca sigur o sa-mi returneze un singur student
    public Student getStudentById(UUID id) {
        return studentsRepository.findStudentById(id);
    }

    // Q: asta nu ar trebui in GradeService? ca si toate cele de mai jos, avand in vedere ca returneaza Grade - A: idk, apeleaza functii din studentService, idk what to say
    public Grade getGradeById(UUID id, int gradeId) {
        Student student = studentsRepository.findStudentById(id);
        Grade grade = student.getGradeById(gradeId);
        if (grade != null) {
            return grade;
        } else {
            throw new IllegalStateException("There is no grade with the id " + gradeId);
        }
    }

    @Transactional
    public Grade addGrade(UUID id, Grade grade) {
        Student student = getStudentById(id);
        student.addGrade(grade);
        return grade;
    }

    @Transactional
    public Grade deleteGrade(UUID id, int gradeId) {
        try {
            return getGradeById(id, gradeId).setDeleted();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public Grade updateGrade(UUID id, Integer value, String evaluationDate, int gradeId) {
        Student student = studentsRepository.findStudentById(id);

        if (student == null) {
            return null;
        }

        Grade grade = getGradeById(id, gradeId);

        if (value != null && (value < 1 || value > 10)) {
            throw new IllegalStateException("The value is not between 1 and 10");
        }

        if (value != null && value != grade.getValue()) {
            grade.setValue(value);
        }

        if (evaluationDate != null && !evaluationDate.equals(grade.getEvaluationDate())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDate.parse(evaluationDate, formatter);
                grade.setEvaluationDate(evaluationDate);
            } catch (DateTimeParseException exception) {
                grade.setEvaluationDate("01.01.1980");
            }
        }
        return getGradeById(id, gradeId);
    }

}
