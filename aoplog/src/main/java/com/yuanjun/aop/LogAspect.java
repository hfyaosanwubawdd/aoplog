package com.yuanjun.aop;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.yuanjun.anno.SystemServiceLog;
import com.yuanjun.aop.exception.ExceptionAspect;
import com.yuanjun.bean.ExceptionLog;
import com.yuanjun.bean.Log;
import com.yuanjun.log.service.ExceptionLogService;
import com.yuanjun.log.service.LogService;
import com.yuanjun.log.util.IpUtil;
/**
 * 日志切面类
 * @ClassName:LogAspect
 * @Description :TODO
 * @author yuanjun
 * @date 2018-1-5
 */
public class LogAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);
	
	@Autowired
	private LogService logService;

	@Autowired
	private ExceptionLogService exceptionLogService;
	 //获取开始时间
    private long BEGIN_TIME ;

    //获取结束时间
    private long END_TIME;
    

    
    //定义本次log实体
    private Log log = new Log();
	
    /**
     * 前置通知
     */
	public void before() {
		 BEGIN_TIME = new Date().getTime();
	     System.out.println("开始");
	}
	/**
	 * 后置通知
	 * @param joinPoint
	 * @throws Exception
	 */
	public void after(JoinPoint joinPoint) throws Exception{
		END_TIME = new Date().getTime();
	    System.out.println("结束");
	}
	/**
	 * 放回值
	 */
	public void afterReturn(){
	   if(log.getState()==1||log.getState()==-1){
            log.setOperationTime((END_TIME-BEGIN_TIME));
            log.setOperationDate(new Date(BEGIN_TIME));
            System.out.println(">>>>>>>>>>存入到数据库");
            logService.insertLog(log);
        }else {
            System.out.println(">>>>>>>>不存入到数据库");
            logService.insertLog(log);
        }
	}
	
	public void doAfterThrow(Exception ex){
		System.out.println(ex);
        System.out.println("例外通知-----------------------------------");
    }
	
	/**
	 * 
	 * @param joinPoint
	 * @return
	 */
	public Object aroud(ProceedingJoinPoint joinPoint){
		  //日志实体对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Object object = null;
		try {
			Map<String, Object> map = getServiceOperation(joinPoint);
			//获取操作类型
			String operationType = (String) map.get("operationType");
			//获取操作描述
			String operationName = (String) map.get("operationName");
			//获取操作的IP地址
			String ip = IpUtil.getRemortIP(request);
			log.setIp(ip);
			log.setOperationName(operationName);
			log.setOperationType(operationType);
	        try {
				object = joinPoint.proceed();
				log.setDescription("执行成功");
				log.setState( 1);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				log.setDescription("执行失败");
				log.setState(-1);
				handleException(joinPoint, e);
			}

		} catch (Exception e) {
		}
        return object;
	}
	
	
	/**
	 * 通过连接点获取注解的信息，即操作的类型与描述
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> getServiceOperation(JoinPoint joinPoint)    
            throws Exception {    
       String targetName = joinPoint.getTarget().getClass().getName();    
       String methodName = joinPoint.getSignature().getName();    
       Object[] arguments = joinPoint.getArgs();    
       Class targetClass = Class.forName(targetName);    
       Method[] methods = targetClass.getMethods();    
       Map<String,Object> map = new HashMap<String,Object>();
       String operationType = "";
       String operationName = "";
        for (Method method : methods) {    
            if (method.getName().equals(methodName)) {    
               Class[] clazzs = method.getParameterTypes();    
                if (clazzs.length == arguments.length) {    
                	operationType = method.getAnnotation(SystemServiceLog. class).operationType(); 
                	operationName = method.getAnnotation(SystemServiceLog. class).operationName();    
                    map.put("operationType", operationType);
                    map.put("operationName", operationName);
                	break;    
               }    
           }    
       }    
        return map; 
	}
	/**
	 * 异常日志处理
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
	/**
     * 
     * @param detailErrMsg 详细错误信息
     * @param method 方法名称
     * @Description: 日志异常
     * @author： Ma
     * @createTime： 2016-10-14
     */
    public static void writeLog(String detailErrMsg,JoinPoint joinPoint,Throwable ex){
        
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
        
        String cla=joinPoint.getTarget().getClass().getName();//action
        String method=joinPoint.getSignature().getName();//method
        try{
            
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //创建输出异常log日志
            File file =new File("d:/logs/"+System.currentTimeMillis()+method+".log");
            //if file doesnt exists, then create it
            if(!file.exists()){
                file.createNewFile();
            }
            
            FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true        
            //日志具体参数
            StringBuffer sb=new StringBuffer();
            sb.append("-----------"+sdf.format(new Date())+"------------\r\n");
            sb.append("远程请求URL["+requestURL+"]\r\n");
            sb.append("接口方法：["+cla+"."+method+"]\r\n");
            sb.append("详细错误信息："+ex+"\r\n");
            //sb.append(detailErrMsg+"\r\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
            
            System.out.println("Done");

         }catch(IOException e){
             e.printStackTrace();
         }
        
        
    }
	
}
