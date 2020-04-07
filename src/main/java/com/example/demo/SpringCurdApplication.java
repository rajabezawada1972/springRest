package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringCurdApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCurdApplication.class, args);
	}

}
