package com.example.securityModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@EnableJpaRepositories(basePackages = "com.example.securityModule.repository")
public class SecurityModuleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SecurityModuleApplication.class, args);

	}
	//private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private RetrieveUserService retrieveUserService;
	@Autowired
	private GradeService gradeService;

	@Override
	public void run(String... args) throws Exception {

		//Aici ar trebui sa primim cumva de la modulul de Log-In
		Mail mail = new Mail("test12310viscol@gmail");
		Password password = new Password("zapada");
		//userService = new UserService(userRepository);
		User user = new User(2, "vreau", "acasa", mail.getMail(), password.getPassword());
		userService.createUser(user);


		gradeService.addGrade(user, 1, 6, 2, 2);
		//System.out.println(retrieveUserService.findUser(mail, password));
	}

}
