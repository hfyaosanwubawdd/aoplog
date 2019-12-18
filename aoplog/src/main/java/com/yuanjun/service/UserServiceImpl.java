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

	@SystemServiceLog(operationType="��ѯ",operationName="��ѯ�����û�")
	public List<User> findAll() {
		System.out.println("excute PersonService");
		return userDao.findAll();
	}
	@SystemServiceLog(operationType="����",operationName="����û�")
	public void saveUser(User user) {
		userDao.saveUser(user);
	}
	
	public void deleteUser(int id){
		userDao.deleteUser(id);
	}
	
	@SystemServiceLog(operationType="��ѯ",operationName="��½��ѯ")
	public void updateUser(User user){
		userDao.updateUser(user);
	}




	
}
