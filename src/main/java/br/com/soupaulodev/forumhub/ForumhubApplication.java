package br.com.soupaulodev.forumhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the ForumHub application.
 * <p>
 * This class is responsible for starting the Spring Boot application and initializing the necessary components.
 * It serves as the entry point for the application and configures the application context.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@SpringBootApplication
public class ForumhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumhubApplication.class, args);
	}

}
