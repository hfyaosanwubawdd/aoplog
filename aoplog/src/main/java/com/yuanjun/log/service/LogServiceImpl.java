package com.yuanjun.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanjun.bean.Log;
import com.yuanjun.dao.LogDao;
@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;
	
	
	public LogDao getLogDao() {
		return logDao;
	}


	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}


	public void insertLog(Log log) {
		logDao.insertLog(log);
	}

}
