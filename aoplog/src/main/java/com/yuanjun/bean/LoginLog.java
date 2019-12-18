package com.yuanjun.bean;

import java.util.Date;

/**
 * 
 * @ClassName:LoginLog
 * @Description :��½��־
 * @author yuanjun
 * @date 2018-1-5
 */
public class LoginLog {
	private int id;
	
	private String loginName;//��½��
	
	private String loginIp;//��½ip
	
	private Date loginTime;//��½ʱ��
	
	private Date loginOutTime;//�˳�ʱ��
	
	private String loginStatus;//��½״̬

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLoginOutTime() {
		return loginOutTime;
	}

	public void setLoginOutTime(Date loginOutTime) {
		this.loginOutTime = loginOutTime;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String toString() {
		return "LoginLog [id=" + id + ", loginName=" + loginName + ", loginIp="
				+ loginIp + ", loginTime=" + loginTime + ", loginOutTime="
				+ loginOutTime + ", loginStatus=" + loginStatus + "]";
	}
	
	
	
}
