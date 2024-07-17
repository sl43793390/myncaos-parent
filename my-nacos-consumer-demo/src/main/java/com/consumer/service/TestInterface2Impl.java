package com.consumer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mynacos.annotation.MyNacosService;
import com.mynacos.entity.User;
import com.mynacos.service.TestInterface2;

@Service
@MyNacosService
public class TestInterface2Impl implements TestInterface2{

	@Override
	public String getName(String id) {
		return id+"-success-consumer";
	}

	@Override
	public User getUserById(String userId) {
		User user = new User("testID", "testName-consumer");
		user.setCreateTime(new Date());
		user.setEmail("123@qwer.com");
		return user;
	}

	@Override
	public String insertUser(User user) {
		return "success-consumer";
	}

	@Override
	public boolean deleteUser(User user) {
		return false;
	}
	@Override
	public List<User> getUsers(String id, User user) {
		System.out.println(id);
		System.out.println(user.toString());
		User user1 = new User("testID", "testName-consumer");
		user1.setCreateTime(new Date());
		user1.setEmail("123@qwer.com");
		User user2 = new User("testID2", "testName2-consumer");
		user2.setCreateTime(new Date());
		user2.setEmail("123@qwer.com2");
		List<User> res = new ArrayList<>();
		res.add(user);
		res.add(user2);
		return res;
	}

	@Override
	public Map<String, User> getUserForMap(List<String> list) {
		System.out.println(list.size());
		Map<String, User> res = new HashMap<>();
		User user1 = new User("testID", "testName-consumer");
		user1.setCreateTime(new Date());
		user1.setEmail("123@qwer.com");
		User user2 = new User("testID2", "testName2-consumer");
		user2.setCreateTime(new Date());
		user2.setEmail("123@qwer.com2");
		res.put("user1-consumer", user1);
		res.put("user2-consumer", user2);
		return res;
	}

	@Override
	public int getUserCount(Integer id) {
		System.out.println(id);
		return 10345;
	}
	
}
