package com.yuanjun.service;

import java.util.List;

import com.yuanjun.bean.User;

public interface UserService {
	
	List<User> findAll();
	
	void saveUser(User user);
		
}
