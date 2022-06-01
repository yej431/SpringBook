package com.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan()
public class SpringSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSearchApplication.class, args);
	}

}
