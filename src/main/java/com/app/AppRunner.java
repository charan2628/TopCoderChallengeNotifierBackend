package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.config.AppConfig;

@SpringBootApplication
public class AppRunner {
 
	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}
}
