package com.project.client;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.project.presentation.MetroPresentation;
import com.project.presentation.MetroPresentationImpl;

@SpringBootApplication(scanBasePackages = "com.project")
@EntityScan(basePackages = "com.project.bean")
@EnableJpaRepositories(basePackages = "com.project.persistence")
public class MetroSystemLayeredSpringBootJpaProjectApplication  implements CommandLineRunner{
	
	@Autowired
	private MetroPresentation metroPresentation;

	public static void main(String[] args) {
		SpringApplication.run(MetroSystemLayeredSpringBootJpaProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while(true) {
			Scanner scanner=new Scanner(System.in);
			metroPresentation.showMenu();
			System.out.print("Enter Your choice: ");
			int choice=scanner.nextInt();
			metroPresentation.performMenu(choice);
		}
		
	}

}
