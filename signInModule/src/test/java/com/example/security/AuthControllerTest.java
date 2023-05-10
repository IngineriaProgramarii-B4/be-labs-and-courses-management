package com.example.security;

import com.example.security.controllers.AuthController;
import com.example.security.dto.*;
import com.example.security.model.*;
import com.example.security.repository.*;
import com.example.security.security.*;
import com.example.security.service.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTGenerator jwtGenerator;

    @Mock
    private EmailService emailService;

    @Mock
    private UserService userService;

    @Mock
    private StudentService studentService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private HttpServletRequest request;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(
                authenticationManager,
                userRepository,
                roleRepository,
                passwordEncoder,
                jwtGenerator,
                emailService,
                userService,
                studentService,
                teacherService
        );
    }

    @Test
    void testLogin() {
        LoginRequestBody loginRequestBody = new LoginRequestBody();
        loginRequestBody.setEmail("test@example.com");
        loginRequestBody.setPassword("password");

        List<Role> roles = new ArrayList<>();
        roles.add(new Role());

        Authentication authentication = mock(Authentication.class);
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(roles);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByEmail(loginRequestBody.getEmail())).thenReturn(user);
        when(jwtGenerator.generateToken(authentication, roles)).thenReturn("testToken");

        ResponseEntity<AuthResponseDto> responseEntity = authController.login(loginRequestBody, request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("testToken", responseEntity.getBody().getAccessToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(loginRequestBody.getEmail());
        verify(jwtGenerator, times(1)).generateToken(authentication, roles);
    }



    @Test
    void testForgotPassword_UserNotFound() {
        // Mocking the UserRepository to return null
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        String email = "example@example.com";

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail(email);

        ForgotPasswordRequestBody requestBody = new ForgotPasswordRequestBody();
        requestBody.setEmail(emailRequest.getEmail());

        try {
            ResponseEntity<String> response = authController.forgotPassword(requestBody);
            String result = response.getBody();

            // Perform assertions on the result
            assertNotNull(result);
            // Additional assertions based on the expected behavior
            verify(userRepository, times(1)).findByEmail(email);
            // Additional verifications as needed
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
    @Test
    void testResetPasswordRequest_UserFound() {
        UserEntity user = new UserEntity();
        // Mocking the UserRepository to return the user
        when(userRepository.findByEmail(any())).thenReturn(user);

        EmailRequest emailRequest = new EmailRequest();
        // Set the email request properties

        try {
            String result = authController.resetPasswordRequest(emailRequest, request);

            // Perform assertions on the result
            assertNotNull(result);
            // Additional assertions based on the expected behavior
            verify(userRepository, times(1)).findByEmail(any());
            verify(userService, times(1)).createPasswordResetTokenForUser(any(UserEntity.class), anyString());
            // Additional verifications as needed
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
    @Test
    void testRegister_ExistingEmail() {
        RegisterRequestBody registerRequestBody = new RegisterRequestBody();
        registerRequestBody.setEmail("test@example.com");

        when(userRepository.existsByEmail(registerRequestBody.getEmail())).thenReturn(true);

        ResponseEntity<String> responseEntity = authController.register(registerRequestBody);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Email is already in use!", responseEntity.getBody());

        verify(userRepository, times(1)).existsByEmail(registerRequestBody.getEmail());
        verifyNoMoreInteractions(userRepository);
    }



    @Test
    void testRegister_UserAlreadyRegistered() {
        // Mocking the UserRepository to return true for existsById
        when(userRepository.existsById(anyLong())).thenReturn(true);
        // Mocking the UserRepository to return a user with email and password
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password");
        when(userRepository.findByUserId(anyLong())).thenReturn(userEntity);

        RegisterRequestBody registerRequestBody = new RegisterRequestBody();
        registerRequestBody.setUserId(1L);
        registerRequestBody.setEmail("test@example.com");
        registerRequestBody.setPassword("password");

        ResponseEntity<String> responseEntity = authController.register(registerRequestBody);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("User is already registered!", responseEntity.getBody());

        verify(userRepository, times(1)).existsById(registerRequestBody.getUserId());
        verify(userRepository, times(1)).findByUserId(registerRequestBody.getUserId());
    }

    @Test
    void testResetPassword_ValidToken() {
        String token = "validToken";
        String newPassword = "newPassword";

        // Mock the UserService to return "valid" for validatePasswordResetToken and return a user for findUserByPasswordToken
        when(userService.validatePasswordResetToken(token)).thenReturn("valid");
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("oldPassword");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        userEntity.setRoles(roles);
        when(userService.findUserByPasswordToken(token)).thenReturn(userEntity);

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setNewPassword(newPassword);

        ResponseEntity<String> responseEntity = authController.resetPassword(passwordResetRequest, token);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Password has been reset successfully", responseEntity.getBody());

        verify(userService, times(1)).validatePasswordResetToken(token);
        verify(userService, times(1)).findUserByPasswordToken(token);
        verify(userService, times(1)).resetPassword(any(UserEntity.class), eq(newPassword));
        // Additional verifications as needed
    }
    @Test
    void testRegister_UserNotFound() {
        RegisterRequestBody registerRequestBody = new RegisterRequestBody();
        registerRequestBody.setUserId(1L);
        registerRequestBody.setEmail("test@example.com");
        registerRequestBody.setPassword("password");

        when(userRepository.existsByEmail(registerRequestBody.getEmail())).thenReturn(false);
        when(userRepository.existsById(registerRequestBody.getUserId())).thenReturn(false);

        ResponseEntity<String> responseEntity = authController.register(registerRequestBody);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User not found!", responseEntity.getBody());

        verify(userRepository, times(1)).existsByEmail(registerRequestBody.getEmail());
        verify(userRepository, times(1)).existsById(registerRequestBody.getUserId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testForgotPassword_Success() {
        String email = "example@example.com";
        UserEntity user = new UserEntity();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(jwtGenerator.generateResetToken(user)).thenReturn("resetToken");

        ForgotPasswordRequestBody requestBody = new ForgotPasswordRequestBody();
        requestBody.setEmail(email);

        ResponseEntity<String> response = authController.forgotPassword(requestBody);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password reset email sent!", response.getBody());

        verify(userRepository, times(1)).findByEmail(email);
        verify(jwtGenerator, times(1)).generateResetToken(user);
    }

    @Test
    void testResetPassword_InvalidToken() {
        String token = "invalidToken";

        when(userService.validatePasswordResetToken(token)).thenReturn("invalid");

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setNewPassword("newPassword");

        ResponseEntity<String> responseEntity = authController.resetPassword(passwordResetRequest, token);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid token password reset token", responseEntity.getBody());

        verify(userService, times(1)).validatePasswordResetToken(token);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void testResetPassword_UserNotFound() {
        String token = "validToken";

        when(userService.validatePasswordResetToken(token)).thenReturn("valid");
        when(userService.findUserByPasswordToken(token)).thenReturn(null);

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setNewPassword("newPassword");

        ResponseEntity<String> responseEntity = authController.resetPassword(passwordResetRequest, token);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid token password reset token", responseEntity.getBody());

        verify(userService, times(1)).validatePasswordResetToken(token);
        verify(userService, times(1)).findUserByPasswordToken(token);
        verifyNoMoreInteractions(userService);
    }
    @Test
    void testRegister_UserExistsEmailAndPasswordNotSet() {
        RegisterRequestBody registerRequestBody = new RegisterRequestBody();
        registerRequestBody.setUserId(1L);
        registerRequestBody.setEmail("test@example.com");
        registerRequestBody.setPassword("password");

        when(userRepository.existsByEmail(registerRequestBody.getEmail())).thenReturn(false);
        when(userRepository.existsById(registerRequestBody.getUserId())).thenReturn(true);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(registerRequestBody.getUserId());
        when(userRepository.findByUserId(registerRequestBody.getUserId())).thenReturn(userEntity);

        // Let's test the role with ID 2 first, i.e., teacher
        Role role = new Role();
        role.setId(2);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        userEntity.setRoles(roles);

        ResponseEntity<String> responseEntity = authController.register(registerRequestBody);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User registered success!", responseEntity.getBody());

        verify(userRepository, times(1)).existsByEmail(registerRequestBody.getEmail());
        verify(userRepository, times(1)).existsById(registerRequestBody.getUserId());
        verify(userRepository, times(1)).findByUserId(registerRequestBody.getUserId());
        verify(userRepository, times(1)).save(userEntity);
        verify(teacherService, times(1)).addTeacher(any(Teacher.class));
    }

    @Test
    void testRegister_UserExistsEmailAndPasswordNotSet_StudentRole() {
        RegisterRequestBody registerRequestBody = new RegisterRequestBody();
        registerRequestBody.setUserId(1L);
        registerRequestBody.setEmail("test@example.com");
        registerRequestBody.setPassword("password");

        when(userRepository.existsByEmail(registerRequestBody.getEmail())).thenReturn(false);
        when(userRepository.existsById(registerRequestBody.getUserId())).thenReturn(true);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(registerRequestBody.getUserId());
        when(userRepository.findByUserId(registerRequestBody.getUserId())).thenReturn(userEntity);

        // This time, let's test the role with ID 3, i.e., student
        Role role = new Role();
        role.setId(3);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        userEntity.setRoles(roles);

        ResponseEntity<String> responseEntity = authController.register(registerRequestBody);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User registered success!", responseEntity.getBody());

        verify(userRepository, times(1)).existsByEmail(registerRequestBody.getEmail());
        verify(userRepository, times(1)).existsById(registerRequestBody.getUserId());
        verify(userRepository, times(1)).findByUserId(registerRequestBody.getUserId());
        verify(userRepository, times(1)).save(userEntity);
        verify(studentService, times(1)).addStudent(any(Student.class));
    }
    @Test
    void testResetPasswordForTeacher() {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setNewPassword("newPassword");

        String token = "resetToken";

        when(userService.validatePasswordResetToken(token)).thenReturn("valid");

        UserEntity theUser = new UserEntity();
        Role role = new Role();
        role.setId(2);
        theUser.setRoles(Arrays.asList(role));
        theUser.setPassword("oldPassword");
        theUser.setUserId(1234L);
        when(userService.findUserByPasswordToken(token)).thenReturn(theUser);

        Teacher teacher = new Teacher();
        when(teacherService.getTeacherByRegistrationNumber(theUser.getUserId())).thenReturn(teacher);

        ResponseEntity<String> responseEntity = authController.resetPassword(passwordResetRequest, token);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Password has been reset successfully", responseEntity.getBody());
    }

    @Test
    void testResetPasswordForStudent() {
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setNewPassword("newPassword");

        String token = "resetToken";

        when(userService.validatePasswordResetToken(token)).thenReturn("valid");

        UserEntity theUser = new UserEntity();
        Role role = new Role();
        role.setId(3);
        theUser.setRoles(Arrays.asList(role));
        theUser.setPassword("oldPassword");
        theUser.setUserId(1234L);


        when(userService.findUserByPasswordToken(token)).thenReturn(theUser);

        Student student = new Student();
        when(studentService.getStudentByRegistrationNumber(theUser.getUserId())).thenReturn(student);

        ResponseEntity<String> responseEntity = authController.resetPassword(passwordResetRequest, token);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Password has been reset successfully", responseEntity.getBody());
    }

     void testRegister_ErrorSavingUser() throws Exception {
        RegisterRequestBody requestBody = new RegisterRequestBody();
        requestBody.setEmail("newuser@example.com");
        requestBody.setUserId(1234L);

        when(userRepository.existsByEmail(requestBody.getEmail())).thenReturn(false);
        when(userRepository.existsById(requestBody.getUserId())).thenReturn(true);
        when(userRepository.findByUserId(requestBody.getUserId())).thenReturn(new UserEntity());
        doThrow(new RuntimeException("Error saving user")).when(userRepository).save(any(UserEntity.class));

        ResponseEntity<String> responseEntity = authController.register(requestBody);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error when saving user!", responseEntity.getBody());
    }

    @Test
    void testResetPassword_UserServiceException() {
        String token = "validToken";
        String newPassword = "newPassword";

        when(userService.validatePasswordResetToken(token)).thenThrow(new RuntimeException("Service error"));

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setNewPassword(newPassword);

        try {
            // Act
            ResponseEntity<String> responseEntity = authController.resetPassword(passwordResetRequest, token);

            // This part should not be reached, because a RuntimeException should be thrown
            fail("Expected an RuntimeException to be thrown");
        } catch (RuntimeException e) {
            // Assert
            assertEquals("Service error", e.getMessage());
        }

        // Verify
        verify(userService, times(1)).validatePasswordResetToken(token);
    }

}



