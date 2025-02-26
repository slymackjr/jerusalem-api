package com.jerusalem.jerusalem_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan("com.jerusalem.jerusalem_api.vo")
//@EnableJpaRepositories(basePackages = "com.jerusalem.jerusalem_api.data.dao")
public class JerusalemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JerusalemApiApplication.class, args);
	}

}
