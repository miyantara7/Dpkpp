package com.app;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DpkppApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("ASIA/JAKARTA"));
		SpringApplication.run(DpkppApplication.class, args);
	}

}
