package com.consumer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.consumer.service.UserService;
import com.mynacos.entity.User;


@SpringBootApplication
@ComponentScan(basePackages = {"com.consumer","com.mynacos"})
public class MyncosConsumerApplication {

	public static void main(String[] args) {
		 ConfigurableApplicationContext run = SpringApplication.run(MyncosConsumerApplication.class, args);
		 UserService bean = run.getBean(UserService.class);
		 int userCount = bean.getUserCount(123);
		 System.out.println(userCount);
		 
			List<String> res = new ArrayList<>();
			res.add("test1");
			res.add("test2");
		 Map<String, User> userForMap = bean.getUserForMap(res);
		 System.out.println(userForMap.size());
		 System.exit(0);
	}

}
