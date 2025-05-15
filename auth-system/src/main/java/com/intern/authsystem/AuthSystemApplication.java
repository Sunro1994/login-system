package com.intern.authsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuthSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthSystemApplication.class, args);
	}

}
