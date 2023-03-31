package com.example.userImpl.student;

import com.example.grades.Grade;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
@Service
public class StudentService {

    private final List<Student> studentList = new ArrayList<>();

    public StudentService(){
        initStudentDataBase();
    }

    // Baza de date fictiva cu doua intrari. Se poate elimina din moment ce exista HTTP PUT handler. Implicit la fel si pentru TeacherService.
    public List<Student> initStudentDataBase() {

        Student student = new Student(0,301232,"student1@gmail.com", "Mihai");
        Student student1 = new Student(1,301233,"student2@gmail.com", "Andrei");
        studentList.add(student); studentList.add(student1);

        return studentList;
    }

    public List<Student> getStudentDataBase(){
        return studentList;
    }
    public Student getStudentById(int id){
        return studentList.get(id);
    }
    public Student save(Student student) {
        studentList.add(student);
        return student;
    }

    public Student delete(Student student){
        try {
            studentList.remove(student);
            return student;
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public void addGrade(int id, Grade grade) {
        studentList.get(id).addGrade(grade);
    }

    public Grade deleteGrade(int id, int gradeId) {
        List<Grade> grades = studentList.get(id).getGrades();
        try {
            Grade toRemove = new Grade();
            for (Grade grade : grades ) {
                if (grade.getId() == gradeId) {
                    toRemove = grade;
                    break;
                }
            }
            grades.remove(toRemove);
            return toRemove;
        }
        catch (Exception e) {
            return null;
        }
    }

    public Grade getGradeById(int id, int gradeId) {
        return studentList.get(id).getGrades().get(gradeId);
    }

    public Grade updateGrade(int id,Integer value,String evaluationDate,Integer gradeId){
        if(value != null) {
            if((value<1 || value>10)) {
                throw new IllegalStateException("The value is not between 1 and 10");
            } else if(value!=getGradeById(id,gradeId).getValue()) {
                getStudentById(id).setGrade(value, gradeId);
            }
        }

        boolean validDate=true;
        if(evaluationDate != null && !evaluationDate.equals(getStudentById(id).getGrades().get(gradeId).getEvaluationDate())){
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDate date=LocalDate.parse(evaluationDate,formatter);
            } catch (DateTimeParseException exception){
                validDate=false;
            }
            if(validDate) {
                getStudentById(id).getGrades().get(gradeId).setEvaluationDate(evaluationDate);
            }
        }
        return getGradeById(id,gradeId);
    }
}
