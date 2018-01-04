package com.cym.security.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CymSecurityBrowserApplication {



	public static void main(String[] args) {
		SpringApplication.run(CymSecurityBrowserApplication.class, args);
	}
}
