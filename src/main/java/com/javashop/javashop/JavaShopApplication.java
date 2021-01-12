package com.javashop.javashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Timer;

@SpringBootApplication
@EnableScheduling
public class JavaShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaShopApplication.class, args);
	}

}
