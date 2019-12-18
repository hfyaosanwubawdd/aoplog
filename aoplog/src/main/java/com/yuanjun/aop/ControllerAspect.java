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
 * @Description :controller���������
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

    
    //���屾��logʵ��
    private LoginLog log = new LoginLog();
	
	public void before(){
		System.out.println("controllerǰ��֪ͨ");
	}
    
	public void afterReturn(){
            log.setLoginTime(new Date());
            loginLogService.saveLoginLog(log);
	}
	
	/**
	 * ���ƴ���
	 * @param joinPoint
	 * @return
	 */
	public Object aroud(ProceedingJoinPoint joinPoint){
		  //��־ʵ�����
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//�û���Ϣ�Ļ�ȡ���ɴ�session�л�ȡ,���ֻ���login�����ģ����Դ������л�ȡ
        String  userName = "����";
        Object object = null;
		try {			
			String ip = IpUtil.getRemortIP(request);
			log.setLoginIp(ip);
			log.setLoginName(userName);
	        try {
				object = joinPoint.proceed();
				//���ݵ�½�߼��Ĵ����������жϵ�½�����Ƿ�ɹ�
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
	 * �����쳣
	 * @param joinPoint
	 * @param ex
	 */
	public  void  handleException(JoinPoint joinPoint,Throwable ex){
		LOGGER.info(">>>>>>ϵͳ�쳣����¼�쳣��Ϣ�����ݿ�------start------"); 
		ExceptionLog log = new ExceptionLog();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.
				getRequestAttributes()).getRequest(); 
		//��ȡ�����URL
		StringBuffer requestURL = request.getRequestURL();
		//��ȡ�� ����Ϣ
		String queryString = request.getQueryString(); 
		//��װ��������URL������
		if(queryString != null){  
			requestURL .append("?").append(queryString);  
		}
		String ip = IpUtil.getRemortIP(request);
		//������
		String className = joinPoint.getTarget().getClass().getName();
		//ִ�еķ���
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
	    LOGGER.info(">>>>>>ϵͳ�쳣����¼�쳣��Ϣ�����ݿ�------end------");  
       
	}
}
