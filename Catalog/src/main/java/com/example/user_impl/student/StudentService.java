package com.example.user_impl.student;

import com.example.grades.Grade;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@Component
public class StudentService {

    private final StudentRepository studentRepository;

    private static final String ERROR_MESSAGE="There is no student with the id : ";

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudentDataBase(){
        return studentRepository.getAllStudents();
    }
    public Student getStudentById(int id){
        return this.studentRepository.findById(id).orElse((Student)null);
    }


    public Student save(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student delete(Student student){
        try {
            studentRepository.delete(student);
            return student;
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    @Transactional
    public Grade addGrade(int id, Grade grade) {
        Student student=studentRepository.findById(id).orElseThrow(()->new IllegalStateException(ERROR_MESSAGE+id));
        student.addGrade(grade);
        return grade;
    }

    @Transactional
    public Grade deleteGrade(int id, int gradeId) {
        List<Grade> grades;
        Student student=studentRepository.findById(id).orElseThrow(()-> new IllegalStateException(ERROR_MESSAGE+id));
        grades=student.getGrades();
        try {
            return getGradeById(id,gradeId).setDeleted();
        } catch (Exception e) {
            return null;
        }
    }

    public Grade getGradeById(int id, int gradeId) {
        Student student=studentRepository.findById(id).orElseThrow(() -> new IllegalStateException(ERROR_MESSAGE+id));
        Grade grade=student.getGradeById(gradeId);
        if(grade != null){
            return grade;
        } else{
            throw new IllegalStateException("There is no grade with the id "+gradeId);
        }
    }

    @Transactional
    public Grade updateGrade(int id, Integer value, String evaluationDate, int gradeId) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(ERROR_MESSAGE + id));

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
                // Handle invalid date format
            }
        }

        return getGradeById(id, gradeId);
    }

}
