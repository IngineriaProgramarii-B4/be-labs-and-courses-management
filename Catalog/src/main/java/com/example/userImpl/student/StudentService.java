package com.example.userImpl.student;

import com.example.grades.Grade;
import com.example.grades.GradeRepository;
import com.example.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class StudentService {

    private final StudentRepository studentRepository;
    private GradeRepository gradeRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository,GradeRepository gradeRepository){
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }


    public List<Student> getStudentDataBase(){
        return studentRepository.getAllStudents();
    }
    public Student getStudentById(int id){
        return (Student) this.studentRepository.findById(id).orElse((Student)null);
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
        Student student=studentRepository.findById(id).orElseThrow(()->new IllegalStateException("Student with id "+id+" doesn't exist"));
        student.addGrade(grade);
        return grade;
    }

    @Transactional
    public Grade deleteGrade(int id, int gradeId) {
        List<Grade> grades;
        Student student=studentRepository.findById(id).orElseThrow(()-> new IllegalStateException("Student with id "+id+" doesn't exist"));
        grades=student.getGrades();
        try {
            Grade toRemove = new Grade();
//                for (Grade grade : grades) {
//                    if (grade.getId() == gradeId) {
//                        toRemove = grade;
//                        break;
//                    }
//                }
            toRemove=getGradeById(id,gradeId);
            grades.remove(toRemove);
            return toRemove;
        } catch (Exception e) {
            return null;
        }
    }

    public Grade getGradeById(int id, int gradeId) {
        Student student=studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Student with id "+id+"does not exist"));
        //return student.getGrades().get(gradeId);
        Grade grade=student.getGradeById(gradeId);
        if(grade != null){
            return grade;
        } else{
            throw new IllegalStateException("There is no grade with the id "+gradeId);
        }
        //return studentRepository.findById(id).get().getGrades().get(gradeId);
    }

    @Transactional
    public Grade updateGrade(int id,Integer value,String evaluationDate,int gradeId) {
        Student student=studentRepository.findById(id).orElseThrow(()->new IllegalStateException("Student with id "+id+" does not exist"));
        Integer originalId=gradeId;
        Grade grade=getGradeById(id,gradeId);
        if(value != null) {
            if((value<1 || value>10)) {
                throw new IllegalStateException("The value is not between 1 and 10");
            } else if(value!=getGradeById(id,gradeId).getValue()) {
                grade.setValue(value);
                //getGradeById(id,gradeId).setValue(value);
            }
        }

        boolean validDate=true;
        if(evaluationDate != null && !evaluationDate.equals(grade.getEvaluationDate())){
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDate date=LocalDate.parse(evaluationDate,formatter);
            } catch (DateTimeParseException exception){
                validDate=false;
            }
            if(validDate) {
                //student.getGrades().get(gradeId).setEvaluationDate(evaluationDate);
                //getGradeById(id,gradeId).setEvaluationDate(evaluationDate);
                grade.setEvaluationDate(evaluationDate);
            }
        }
        //studentRepository.saveAll(studentRepository.getAllStudents());
        return getGradeById(id,gradeId);
    }
}
