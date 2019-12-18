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
 * ��־������
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
	 //��ȡ��ʼʱ��
    private long BEGIN_TIME ;

    //��ȡ����ʱ��
    private long END_TIME;
    

    
    //���屾��logʵ��
    private Log log = new Log();
	
    /**
     * ǰ��֪ͨ
     */
	public void before() {
		 BEGIN_TIME = new Date().getTime();
	     System.out.println("��ʼ");
	}
	/**
	 * ����֪ͨ
	 * @param joinPoint
	 * @throws Exception
	 */
	public void after(JoinPoint joinPoint) throws Exception{
		END_TIME = new Date().getTime();
	    System.out.println("����");
	}
	/**
	 * �Ż�ֵ
	 */
	public void afterReturn(){
	   if(log.getState()==1||log.getState()==-1){
            log.setOperationTime((END_TIME-BEGIN_TIME));
            log.setOperationDate(new Date(BEGIN_TIME));
            System.out.println(">>>>>>>>>>���뵽���ݿ�");
            logService.insertLog(log);
        }else {
            System.out.println(">>>>>>>>�����뵽���ݿ�");
            logService.insertLog(log);
        }
	}
	
	public void doAfterThrow(Exception ex){
		System.out.println(ex);
        System.out.println("����֪ͨ-----------------------------------");
    }
	
	/**
	 * 
	 * @param joinPoint
	 * @return
	 */
	public Object aroud(ProceedingJoinPoint joinPoint){
		  //��־ʵ�����
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Object object = null;
		try {
			Map<String, Object> map = getServiceOperation(joinPoint);
			//��ȡ��������
			String operationType = (String) map.get("operationType");
			//��ȡ��������
			String operationName = (String) map.get("operationName");
			//��ȡ������IP��ַ
			String ip = IpUtil.getRemortIP(request);
			log.setIp(ip);
			log.setOperationName(operationName);
			log.setOperationType(operationType);
	        try {
				object = joinPoint.proceed();
				log.setDescription("ִ�гɹ�");
				log.setState( 1);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				log.setDescription("ִ��ʧ��");
				log.setState(-1);
				handleException(joinPoint, e);
			}

		} catch (Exception e) {
		}
        return object;
	}
	
	
	/**
	 * ͨ�����ӵ��ȡע�����Ϣ��������������������
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
	 * �쳣��־����
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
	/**
     * 
     * @param detailErrMsg ��ϸ������Ϣ
     * @param method ��������
     * @Description: ��־�쳣
     * @author�� Ma
     * @createTime�� 2016-10-14
     */
    public static void writeLog(String detailErrMsg,JoinPoint joinPoint,Throwable ex){
        
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
            //sb.append(detailErrMsg+"\r\n");
            out.write(sb.toString().getBytes("utf-8"));//ע����Ҫת����Ӧ���ַ���
            out.close();
            
            System.out.println("Done");

         }catch(IOException e){
             e.printStackTrace();
         }
        
        
    }
	
}
