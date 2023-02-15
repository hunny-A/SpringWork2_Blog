package com.sparta.springwork2_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class SpringWork2BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWork2BlogApplication.class, args);
	}

}
