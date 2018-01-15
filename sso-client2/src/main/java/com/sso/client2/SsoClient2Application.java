package com.sso.client2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@RestController
//@EnableOAuth2Sso
public class SsoClient2Application {

	@GetMapping("/user")
	public String user() {
		return "11111111";
	}

	public static void main(String[] args) {
		SpringApplication.run(SsoClient2Application.class, args);
	}
}
