package com.mynacos.service;

import java.util.List;
import java.util.Map;

import com.mynacos.entity.User;

public interface TestInterface1 {

	String getName(String id);
	
	User getUserById(String userId);
	
	String insertUser(User user);
	
	List<User> getUsers(String id,User user);
	
	Map<String, User> getUserForMap(List<String> list);
	
	int getUserCount(Integer id);
	
	boolean deleteUser(User user);
}
