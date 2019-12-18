package com.yuanjun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanjun.anno.SystemServiceLog;
import com.yuanjun.bean.User;
import com.yuanjun.dao.UserDao;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	
	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@SystemServiceLog(operationType="查询",operationName="查询所有用户")
	public List<User> findAll() {
		System.out.println("excute PersonService");
		return userDao.findAll();
	}
	@SystemServiceLog(operationType="插入",operationName="添加用户")
	public void saveUser(User user) {
		userDao.saveUser(user);
	}
	
	public void deleteUser(int id){
		userDao.deleteUser(id);
	}
	
	@SystemServiceLog(operationType="查询",operationName="登陆查询")
	public void updateUser(User user){
		userDao.updateUser(user);
	}




	
}
