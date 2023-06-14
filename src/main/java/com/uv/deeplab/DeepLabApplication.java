package com.uv.deeplab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DeepLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeepLabApplication.class, args);
	}

}
