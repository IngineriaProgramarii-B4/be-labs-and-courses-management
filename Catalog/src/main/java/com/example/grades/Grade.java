package com.example.grades;

import com.example.subject.Subject;

public class Grade {
    private int gradeId;
    private int value;
    private Subject subject;

    private String evaluationDate;
    public Grade(){}

    public Grade(int value, Subject subject, String evaluationDate) {
        this.value = value;
        this.subject = subject;
        this.evaluationDate = evaluationDate;

    }

    public int getId() {
        return gradeId;
    }
    public void setId(int gradeId) {
        this.gradeId = gradeId;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                "value=" + value +
                ", subject=" + subject +
                ", evaluation date=" + evaluationDate +
                '}';
    }
}
