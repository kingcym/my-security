package com.cym.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CymSecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CymSecurityAppApplication.class, args);
	}
}
