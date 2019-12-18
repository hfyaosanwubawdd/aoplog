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
	@SystemServiceLog(operationName="��ѯ���е��û���Ϣ",operationType="��ѯ")
	public String test(){
		List<User> list = userService.findAll();
		for (User user : list) {
			System.out.println(user);
		}
		return "main";
	}
	@RequestMapping("/add")
	@SystemServiceLog(operationName="����û�",operationType="���")
	public String insert(){
		User user = new User();
		user.setId(10);
		user.setName("�Ժ�");
		user.setPassword("123456");
		user.setAddress("shanghai");
		userService.saveUser(user);
		return "main";
	}
	@RequestMapping("/exception")
	public void exception(){
		throw new IllegalArgumentException("name�����ĳ��ȱ������3��С��10��"); 
	}
	
	@RequestMapping("/login")
	public String login(String userName,String password){
		//�򵥵�չʾ�˵�½�߼��������Լ���Ŀ��ʵ�ʴ��������½����ɹ��ı�ʶ����
		//AOP�Ĵ�����һ�£���Ҫ��Ϊ�˻�ȡ��½��־���Ƿ��½�ɹ����ֶ���Ϣ
		if("yuanjun".equals(userName)&&"123456".equals(password)){
			return "main";
		}
		return "error";
		
	}
	
}
