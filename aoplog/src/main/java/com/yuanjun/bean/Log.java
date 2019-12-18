package com.yuanjun.bean;

import java.util.Date;

/**
 * 
 * @ClassName:Log
 * @Description :������־
 * @author yuanjun
 * @date 2018-1-4
 */
public class Log {
	
	private int logid;
	
	private String ip;//�����˵�ip
	
	private String operateUserName;//������
	
	private String operationName;//������
	
	private String operationType;//��������
	
	private Date  operationDate;//����ʱ��
	
	private long operationTime;//����ʱ��
	
	private int state;//����״̬
	
	private String description;//��������

	public int getLogid() {
		return logid;
	}

	public void setLogid(int logid) {
		this.logid = logid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public long getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(long operationTime) {
		this.operationTime = operationTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Log [logid=" + logid + ", ip=" + ip + ", operateUserName="
				+ operateUserName + ", operationName=" + operationName
				+ ", operationType=" + operationType + ", operationDate="
				+ operationDate + ", operationTime=" + operationTime
				+ ", state=" + state + ", description=" + description + "]";
	}

}
