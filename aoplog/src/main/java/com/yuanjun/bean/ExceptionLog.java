package com.yuanjun.bean;

import java.util.Date;

/**
 * 
 * @ClassName:ExceptionLog
 * @Description :�쳣��Ϣ��־��
 * @author yuanjun
 * @date 2018-1-5
 */
public class ExceptionLog {

	private int id;
	
	private String ip;//�����ip��ַ
	
	private String url;//�����url
	
	private String args;//�������
	
	private String className;//�����쳣������
	
	private String methodName;//ִ�еķ�����
	
	private String exceptionType;//�쳣����
	
	private Date  exceptionTime;//�����쳣ʱ��
	
	private String exceptionMsg;//�쳣��Ϣ
	
	private byte isView;//�Ƿ�鿴

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public Date getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public byte getIsView() {
		return isView;
	}

	public void setIsView(byte isView) {
		this.isView = isView;
	}

	@Override
	public String toString() {
		return "ExceptionLog [id=" + id + ", ip=" + ip + ", url=" + url
				+ ", args=" + args + ", className=" + className
				+ ", methodName=" + methodName + ", exceptionType="
				+ exceptionType + ", exceptionTime=" + exceptionTime
				+ ", exceptionMsg=" + exceptionMsg + ", isView=" + isView + "]";
	}
	
	
}
