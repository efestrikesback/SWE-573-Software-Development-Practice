package com.devcom.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("com.devcom.webapp.models")
@ComponentScan(basePackages = { "com.devcom.webapp.models" })
@EntityScan(basePackages = {"com.devcom.webapp.models"})
@SpringBootApplication

public class DevcomWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevcomWebApplication.class, args);
	}

}
