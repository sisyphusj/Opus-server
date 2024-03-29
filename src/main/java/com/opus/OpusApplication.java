package com.opus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:opus.properties")
public class OpusApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpusApplication.class, args);
	}

}
