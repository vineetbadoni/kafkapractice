package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		start();
	}

	public static void start() {
		SpringApplication.run(DemoApplication.class);
	}

}
