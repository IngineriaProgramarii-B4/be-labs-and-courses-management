package com.example.services;

import com.example.models.Admin;
import com.example.models.AppUser;
import com.example.models.Teacher;
import com.example.models.Student;
import com.example.repository.AdminsRepository;
import com.example.repository.TeachersRepository;
import com.example.repository.StudentsRepository;
import com.example.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final StudentsRepository studentsRepository;
    private final AdminsRepository adminsRepository;
    private final TeachersRepository teachersRepository;

    public UsersService(UsersRepository usersRepository, StudentsRepository studentsRepository, AdminsRepository adminsRepository, TeachersRepository teachersRepository) {
        this.usersRepository = usersRepository;
        this.studentsRepository = studentsRepository;
        this.adminsRepository = adminsRepository;
        this.teachersRepository = teachersRepository;
    }

    public List<AppUser> getUsersByParams(Map<String, Object> params) {
        Integer id = 0;

        if (params.containsKey("id"))
            if (!params.get("id").equals("")) id = Integer.parseInt((String) params.get("id"));

        String firstname = (String) params.get("firstname");
        String lastname = (String) params.get("lastname");
        String email = (String) params.get("email");
        String username = (String) params.get("username");

        List<AppUser> users = usersRepository.findUserByParams(id, firstname, lastname, email, username);
        return users;
    }

    public List<Student> getStudentsByParams(Map<String, Object> params) {
        Integer id = 0;

        if (params.containsKey("id"))
            if (!params.get("id").equals("")) id = Integer.parseInt((String) params.get("id"));

        String firstname = (String) params.get("firstname");
        String lastname = (String) params.get("lastname");
        String email = (String) params.get("email");
        String username = (String) params.get("username");

        Integer year = 0;

        if (params.containsKey("year"))
            if (!params.get("year").equals("")) year = Integer.parseInt((String) params.get("year"));

        Integer semester = 0;

        if (params.containsKey("semester"))
            if (!params.get("semester").equals("")) semester = Integer.parseInt((String) params.get("semester"));

        String registrationNumber = (String) params.get("registrationNumber");

        List<Student> students = studentsRepository.findStudentsByParams(id, firstname, lastname, email, username, year, semester, registrationNumber);

        return students;
    }

    public List<Admin> getAdminsByParams(Map<String, Object> params) {
        Integer id = 0;

        if (params.containsKey("id"))
            if (!params.get("id").equals("")) id = Integer.parseInt((String) params.get("id"));

        String firstname = (String) params.get("firstname");
        String lastname = (String) params.get("lastname");
        String email = (String) params.get("email");
        String username = (String) params.get("username");

        String office = (String) params.get("office");

        String department = (String) params.get("department");

        List<Admin> admins = adminsRepository.findAdminsByParams(id, firstname, lastname, email, username, office, department);

        return admins;
    }

    public List<Teacher> getProfessorByParams(Map<String, Object> params) {
        Integer id = 0;

        if (params.containsKey("id"))
            if (!params.get("id").equals("")) id = Integer.parseInt((String) params.get("id"));

        String firstname = (String) params.get("firstname");
        String lastname = (String) params.get("lastname");
        String email = (String) params.get("email");
        String username = (String) params.get("username");

        String office = (String) params.get("office");

        String title = (String) params.get("title");

        List<Teacher> teachers = teachersRepository.findProfessorsByParams(id, firstname, lastname, email, username, office, title);

        return teachers;
    }

    public void saveStudent(Student stud) {
        studentsRepository.save(stud);
    }
}