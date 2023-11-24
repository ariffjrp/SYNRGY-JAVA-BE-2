package binarfud.Challenge_5;

import binarfud.Challenge_5.controller.HomeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Challenge5Application {

	public static void main(String[] args) {
		HomeController homeController = SpringApplication.run(Challenge5Application.class, args)
				.getBean(HomeController.class);
		homeController.home();
	}
}
