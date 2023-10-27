package binarfud.Challenge_4;

import binarfud.Challenge_4.controller.HomeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Challenge4Application {

	public static void main(String[] args) {
		HomeController homeController = SpringApplication.run(Challenge4Application.class, args)
				.getBean(HomeController.class);
		homeController.home();
	}

}
