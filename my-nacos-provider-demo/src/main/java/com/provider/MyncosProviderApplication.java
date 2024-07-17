package com.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mynacos.entity.User;
import com.provider.service.UserService;


@SpringBootApplication
@ComponentScan(basePackages = {"com.provider","com.mynacos"})
public class MyncosProviderApplication {

	public static void main(String[] args) {
		 ConfigurableApplicationContext run = SpringApplication.run(MyncosProviderApplication.class, args);

		 
	}

}
