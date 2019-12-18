package com.yuanjun.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanjun.bean.LoginLog;
import com.yuanjun.dao.LoginLogDao;
/**
 * 
 * @ClassName:LoginLogServiceImpl
 * @Description :TODO
 * @author yuanjun
 * @date 2018-1-5
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
	
	@Autowired
	private LoginLogDao loginLogDao;
	
	
	public LoginLogDao getLoginLogDao() {
		return loginLogDao;
	}


	public void setLoginLogDao(LoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
	}


	public void saveLoginLog(LoginLog log) {
		loginLogDao.saveLoginLog(log);
	}

}
