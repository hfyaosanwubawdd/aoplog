package com.yuanjun.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanjun.bean.ExceptionLog;
import com.yuanjun.dao.ExceptionLogDao;
@Service
public class ExceptionLogServiceImpl implements ExceptionLogService {
	
	@Autowired
	private ExceptionLogDao exceptionLogDao;
	
	
	public ExceptionLogDao getExceptionLogDao() {
		return exceptionLogDao;
	}


	public void setExceptionLogDao(ExceptionLogDao exceptionLogDao) {
		this.exceptionLogDao = exceptionLogDao;
	}


	public void saveExceptionLog(ExceptionLog log) {
		exceptionLogDao.saveExceptionLog(log);
	}

}
