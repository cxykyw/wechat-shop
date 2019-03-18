package com.kyw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MobileServer {

	public static void main(String[] args) {
		SpringApplication.run(MobileServer.class, args);
	}
}
