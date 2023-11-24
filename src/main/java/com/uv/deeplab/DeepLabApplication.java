package com.uv.deeplab;

import com.uv.deeplab.Service.SupportFunctions.SubscriptorsRos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DeepLabApplication {


	public static void main(String[] args) {
		SpringApplication.run(DeepLabApplication.class, args);
	}

}
