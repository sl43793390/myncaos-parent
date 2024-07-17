package com.provider.service;

import org.springframework.stereotype.Component;

import com.mynacos.annotation.MyNacosReference;
import com.mynacos.entity.User;
import com.mynacos.service.TestInterface1;

@Component
public class UserService {

	@MyNacosReference
	private TestInterface1 interface1;
	
	
	public String getName(String id) {
		String name = interface1.getName(id);
		return name;
	}
	
	public User getUserById(String id) {
		return interface1.getUserById(id);
	}
	
	public String insertUser(User user) {
		return interface1.insertUser(user);
	}
}
