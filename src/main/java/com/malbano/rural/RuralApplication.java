package com.malbano.rural;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RuralApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuralApplication.class, args);
	}

}
