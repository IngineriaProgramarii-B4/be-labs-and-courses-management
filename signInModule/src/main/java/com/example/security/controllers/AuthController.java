package com.example.security.controllers;

import com.example.security.dto.*;
import com.example.security.model.Role;
import com.example.security.model.Student;
import com.example.security.model.Teacher;
import com.example.security.model.UserEntity;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import com.example.security.security.JWTGenerator;
import com.example.security.service.StudentService;
import com.example.security.service.TeacherService;
import com.example.security.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.example.security.security.ForgotPasswordRequestBody;
import com.example.security.security.EmailService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    private final EmailService emailService;
    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, EmailService emailService, UserService userService, StudentService studentService, TeacherService teacherService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.emailService = emailService;
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestBody loginRequestBody,HttpServletRequest request ) {
        List<Role> roles = new ArrayList<>();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestBody.getEmail(),
                loginRequestBody.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userRepository.findByEmail(loginRequestBody.getEmail());
        if(user !=null && !user.getRoles().isEmpty()){
            roles = user.getRoles();
        }
        String token = jwtGenerator.generateToken(authentication,roles);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestBody registerRequestBody) {
        UserEntity user;
        try {
            if (userRepository.existsByEmail(registerRequestBody.getEmail())) {
                return new ResponseEntity<>("Email is already in use!", HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsById(registerRequestBody.getUserId())) {
                user = userRepository.findByUserId(registerRequestBody.getUserId());
                if (StringUtils.hasText(user.getEmail()) && StringUtils.hasText(user.getPassword())) {
                    return new ResponseEntity<>("User is already registered!", HttpStatus.BAD_REQUEST);
                } else {
                    user.setEmail(registerRequestBody.getEmail());
                    user.setPassword(passwordEncoder.encode(registerRequestBody.getPassword()));
                    userRepository.save(user);
                    List<Role> roles = user.getRoles();
                    int role =  roles.get(0).getId();
                    if(role == 1)
                    {

                    }
                    else if(role == 2){
                        Teacher teacher = new Teacher();
                        teacher.setRegistrationNumber(user.getUserId());
                        teacher.setMail(user.getEmail());
                        teacher.setPassword(user.getPassword());
                        teacherService.addTeacher(teacher);
                    }
                    else if(role == 3){
                        Student student = new Student();
                        student.setRegistrationNumber(user.getUserId());
                        student.setMail(user.getEmail());
                        student.setPassword(user.getPassword());
                        studentService.addStudent(student);
                    }

                }
            } else {
                return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error when saving user!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PostMapping("/sendMail")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestBody forgotPasswordRequestBody) {
        String email = forgotPasswordRequestBody.getEmail();
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        try {
            String resetToken = jwtGenerator.generateResetToken(user);
            emailService.sendPasswordResetEmail(user, resetToken);
            return new ResponseEntity<>("Password reset email sent!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error when sending password reset email!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody EmailRequest emailRequest,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        UserEntity user = userRepository.findByEmail(emailRequest.getEmail());
        String passwordResetUrl = "";
        if (user!=null) {
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user, applicationUrl(servletRequest), passwordResetToken);
        }

        return passwordResetUrl;
    }
    private String passwordResetEmailLink(UserEntity user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/resetPassword?token="+passwordToken;
        emailService.sendPasswordResetEmail(user,url);
        return url;
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                @RequestParam("token") String token){
        String tokenVerificationResult = userService.validatePasswordResetToken(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return new ResponseEntity<>("Invalid token password reset token",HttpStatus.BAD_REQUEST);
        }
        UserEntity theUser = userService.findUserByPasswordToken(token);
        if (theUser!=null) {
            userService.resetPassword(theUser, passwordResetRequest.getNewPassword());
            List<Role> roles = theUser.getRoles();
            int role =  roles.get(0).getId();
            if(role == 2){
                Teacher updatedTeacher = teacherService.getTeacherByRegistrationNumber(theUser.getUserId());
                updatedTeacher.setPassword(theUser.getPassword());
                teacherService.addTeacher(updatedTeacher);

            }
            if(role == 3){
                Student updatedStudent = studentService.getStudentByRegistrationNumber(theUser.getUserId());
                updatedStudent.setPassword(theUser.getPassword());
                studentService.addStudent(updatedStudent);

            }
            return new ResponseEntity<>("Password has been reset successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid token password reset token",HttpStatus.BAD_REQUEST);
    }
    public String applicationUrl(HttpServletRequest request) {
        System.out.println(request.getServerName());
        System.out.println(request.getServerPort());
        System.out.println(request.getContextPath());
        return "http://"+request.getServerName()+":"
                +"3000";
    }

}
