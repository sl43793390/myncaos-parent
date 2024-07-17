package com.consumer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mynacos.annotation.MyNacosReference;
import com.mynacos.entity.User;
import com.mynacos.service.TestInterface1;

@Service
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
	
	public boolean deleteUser(User user) {
		System.out.println(user.toString());
		return false;
	}

	public List<User> getUsers(String id, User user) {
		System.out.println(id);
		System.out.println(user.toString());
		return interface1.getUsers(id, user);
	}

	public Map<String, User> getUserForMap(List<String> list) {
		System.out.println(list.size());
		return interface1.getUserForMap(list);
	}

	public int getUserCount(Integer id) {
		System.out.println(id);
		return interface1.getUserCount(id);
	}
}
