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
 * @Description :ͳһ���쳣chuli
 * @author yuanjun
 * @date 2018-1-4
 */
@Aspect
public class ExceptionAspect {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionAspect.class);
	//��������@RequestMapping����
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void webPointcut(){
			
	}
	
	
  
	/**
     * ����web���쳣����¼�쳣��־���������Ѻ���Ϣ��ǰ��
     * Ŀǰֻ����Exception���Ƿ�Ҫ����Error����������
     *
     * @param e �쳣����
     */
	
    //@AfterThrowing(pointcut = "webPointcut()", throwing = "ex")
    public void handleThrowing(JoinPoint joinPoint,Exception ex) {
    	System.out.println("��������AfterThrowing������...");
        try {
	        //�ж���־�������
	        if(log.isInfoEnabled()){
	            log.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
	        }
	        //��ϸ������Ϣ
	        String errorMsg = "";
	        StackTraceElement[] trace = ex.getStackTrace();
	        for (StackTraceElement s : trace) {
	            errorMsg += "\tat " + s + "\r\n";
	        }
	        System.out.println("�����쳣��Ϣ��"+errorMsg);
	        
	        System.out.println("afterThrow�쳣������ " + joinPoint + "\t" + ex.getMessage());
	        
	        System.out.println("��������AfterThrowing��������������");
	        
	        //д���쳣��־
	        writeLog(errorMsg,joinPoint,ex);
        } catch (Exception e) {
			// TODO: handle exception
		}
    }
    
//    @Around("webPointcut()")
//    public Object around(ProceedingJoinPoint joinPoint){
//    	Object object = null;
//		try {
//			System.out.println("�쳣����");
//			object = joinPoint.proceed();
//		} catch (Throwable e) {
//			
//		}
//		
//    	return object;
//    }
    
    /**
     * 
     * @param detailErrMsg ��ϸ������Ϣ
     * @param method ��������
     * @Description: ��־�쳣
     * @author�� Ma
     * @createTime�� 2016-10-14
     */
    public void writeLog(String detailErrMsg,JoinPoint joinPoint,Exception ex){
        
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
        
        String cla=joinPoint.getTarget().getClass().getName();//action
        String method=joinPoint.getSignature().getName();//method
        try{
            
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //��������쳣log��־
            File file =new File("d:/logs/"+System.currentTimeMillis()+method+".log");
            //if file doesnt exists, then create it
            if(!file.exists()){
                file.createNewFile();
            }
            
            FileOutputStream out=new FileOutputStream(file,false); //���׷�ӷ�ʽ��true        
            //��־�������
            StringBuffer sb=new StringBuffer();
            sb.append("-----------"+sdf.format(new Date())+"------------\r\n");
            sb.append("Զ������URL["+requestURL+"]\r\n");
            sb.append("�ӿڷ�����["+cla+"."+method+"]\r\n");
            sb.append("��ϸ������Ϣ��"+ex+"\r\n");
            sb.append(detailErrMsg+"\r\n");
            out.write(sb.toString().getBytes("utf-8"));//ע����Ҫת����Ӧ���ַ���
            out.close();
            
            System.out.println("Done");

         }catch(IOException e){
             e.printStackTrace();
         }
        
        
    }

}
