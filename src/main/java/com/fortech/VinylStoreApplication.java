package com.fortech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class VinylStoreApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VinylStoreApplication.class, args);
	}
}
