package com.yuanjun.aop;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yuanjun.aop.exception.ExceptionAspect;
import com.yuanjun.bean.ExceptionLog;
import com.yuanjun.bean.LoginLog;
import com.yuanjun.log.service.ExceptionLogService;
import com.yuanjun.log.service.LogService;
import com.yuanjun.log.service.LoginLogService;
import com.yuanjun.log.util.IpUtil;

/**
 * 
 * @ClassName:ControllerAspect
 * @Description :controller的切面控制
 * @author yuanjun
 * @date 2018-1-4
 */

public class ControllerAspect {
private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);
	
	@Autowired
	private LoginLogService loginLogService;
	
	@Autowired
	private LogService logService;

	@Autowired
	private ExceptionLogService exceptionLogService;    

    
    //定义本次log实体
    private LoginLog log = new LoginLog();
	
	public void before(){
		System.out.println("controller前置通知");
	}
    
	public void afterReturn(){
            log.setLoginTime(new Date());
            loginLogService.saveLoginLog(log);
	}
	
	/**
	 * 环绕处理
	 * @param joinPoint
	 * @return
	 */
	public Object aroud(ProceedingJoinPoint joinPoint){
		  //日志实体对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//用户信息的获取，可从session中获取,如果只针对login方法的，可以从请求中获取
        String  userName = "张三";
        Object object = null;
		try {			
			String ip = IpUtil.getRemortIP(request);
			log.setLoginIp(ip);
			log.setLoginName(userName);
	        try {
				object = joinPoint.proceed();
				//根据登陆逻辑的处理结果，来判断登陆操作是否成功
				if("main".equals(object)){
					log.setLoginStatus("success");
				}else{
					log.setLoginStatus("fail");
				}
			} catch (Throwable e) {
				log.setLoginStatus("fail");
				handleException(joinPoint, e);
			}

		} catch (Exception e) {
		}
        return object;
	}
	
	
	/**
	 * 处理异常
	 * @param joinPoint
	 * @param ex
	 */
	public  void  handleException(JoinPoint joinPoint,Throwable ex){
		LOGGER.info(">>>>>>系统异常，记录异常信息到数据库------start------"); 
		ExceptionLog log = new ExceptionLog();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.
				getRequestAttributes()).getRequest(); 
		//获取请求的URL
		StringBuffer requestURL = request.getRequestURL();
		//获取参 数信息
		String queryString = request.getQueryString(); 
		//封装完整请求URL带参数
		if(queryString != null){  
			requestURL .append("?").append(queryString);  
		}
		String ip = IpUtil.getRemortIP(request);
		//代理类
		String className = joinPoint.getTarget().getClass().getName();
		//执行的方法
		String methodName = joinPoint.getSignature().getName();
		//
		Object[] argsList = joinPoint.getArgs();
		StringBuffer args = new StringBuffer();
		for (int i = 0; i < argsList.length; i++) {
			args.append(argsList[i]);
		}
		String exceptionType = ex.getClass().getSimpleName();
		Date exceptionTime = new Date();
		String exceptionMsg = ex.getMessage();
		byte isView = 0;
		log.setIp(ip);
		log.setUrl(requestURL.toString());
		log.setArgs(args.toString());
		log.setClassName(className);
		log.setMethodName(methodName);
		log.setExceptionType(exceptionType);
		log.setExceptionTime(exceptionTime);
		log.setExceptionMsg(exceptionMsg);
		log.setIsView(isView);
		System.out.println(log);
		exceptionLogService.saveExceptionLog(log);
	    LOGGER.info(">>>>>>系统异常，记录异常信息到数据库------end------");  
       
	}
}
