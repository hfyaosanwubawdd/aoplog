package com.yuanjun.aop.exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 
 * @ClassName:ExceptionAspect
 * @Description :统一的异常chuli
 * @author yuanjun
 * @date 2018-1-4
 */
@Aspect
public class ExceptionAspect {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionAspect.class);
	//拦截所有@RequestMapping的类
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void webPointcut(){
			
	}
	
	
  
	/**
     * 拦截web层异常，记录异常日志，并返回友好信息到前端
     * 目前只拦截Exception，是否要拦截Error需再做考虑
     *
     * @param e 异常对象
     */
	
    //@AfterThrowing(pointcut = "webPointcut()", throwing = "ex")
    public void handleThrowing(JoinPoint joinPoint,Exception ex) {
    	System.out.println("进入切面AfterThrowing方法中...");
        try {
	        //判断日志输出级别
	        if(log.isInfoEnabled()){
	            log.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
	        }
	        //详细错误信息
	        String errorMsg = "";
	        StackTraceElement[] trace = ex.getStackTrace();
	        for (StackTraceElement s : trace) {
	            errorMsg += "\tat " + s + "\r\n";
	        }
	        System.out.println("具体异常信息："+errorMsg);
	        
	        System.out.println("afterThrow异常方法名 " + joinPoint + "\t" + ex.getMessage());
	        
	        System.out.println("进入切面AfterThrowing方法结束！！！");
	        
	        //写入异常日志
	        writeLog(errorMsg,joinPoint,ex);
        } catch (Exception e) {
			// TODO: handle exception
		}
    }
    
//    @Around("webPointcut()")
//    public Object around(ProceedingJoinPoint joinPoint){
//    	Object object = null;
//		try {
//			System.out.println("异常处理");
//			object = joinPoint.proceed();
//		} catch (Throwable e) {
//			
//		}
//		
//    	return object;
//    }
    
    /**
     * 
     * @param detailErrMsg 详细错误信息
     * @param method 方法名称
     * @Description: 日志异常
     * @author： Ma
     * @createTime： 2016-10-14
     */
    public void writeLog(String detailErrMsg,JoinPoint joinPoint,Exception ex){
        
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
            sb.append(detailErrMsg+"\r\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
            
            System.out.println("Done");

         }catch(IOException e){
             e.printStackTrace();
         }
        
        
    }

}
