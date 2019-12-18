package com.yuanjun.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanjun.anno.SystemServiceLog;
import com.yuanjun.bean.User;
import com.yuanjun.service.UserService;

@Controller
public class LoginControl {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/main")
	@SystemServiceLog(operationName="查询所有的用户信息",operationType="查询")
	public String test(){
		List<User> list = userService.findAll();
		for (User user : list) {
			System.out.println(user);
		}
		return "main";
	}
	@RequestMapping("/add")
	@SystemServiceLog(operationName="添加用户",operationType="添加")
	public String insert(){
		User user = new User();
		user.setId(10);
		user.setName("赵浩");
		user.setPassword("123456");
		user.setAddress("shanghai");
		userService.saveUser(user);
		return "main";
	}
	@RequestMapping("/exception")
	public void exception(){
		throw new IllegalArgumentException("name参数的长度必须大于3，小于10！"); 
	}
	
	@RequestMapping("/login")
	public String login(String userName,String password){
		//简单的展示了登陆逻辑，根据自己项目的实际处理，这里登陆处理成功的标识，与
		//AOP的处理保持一致，主要是为了获取登陆日志中是否登陆成功的字段信息
		if("yuanjun".equals(userName)&&"123456".equals(password)){
			return "main";
		}
		return "error";
		
	}
	
}
