package com.yuanjun.bean;

import java.util.Date;

/**
 * 
 * @ClassName:LoginLog
 * @Description :登陆日志
 * @author yuanjun
 * @date 2018-1-5
 */
public class LoginLog {
	private int id;
	
	private String loginName;//登陆名
	
	private String loginIp;//登陆ip
	
	private Date loginTime;//登陆时间
	
	private Date loginOutTime;//退出时间
	
	private String loginStatus;//登陆状态

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
