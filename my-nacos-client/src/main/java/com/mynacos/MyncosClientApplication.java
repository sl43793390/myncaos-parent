package com.mynacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
public class MyncosClientApplication {

	public static void main(String[] args) {
		 SpringApplication.run(MyncosClientApplication.class, args);

	}

}
