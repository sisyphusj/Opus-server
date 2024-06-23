package com.opus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케줄링 활성화
@EnableAsync // 비동기 처리 활성화
public class OpusApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpusApplication.class, args);
	}

}
