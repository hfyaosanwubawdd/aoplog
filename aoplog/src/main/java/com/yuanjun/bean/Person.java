package com.yuanjun.bean;
/**
 * 
 * @author Administrator
 *
 */
public class Person {
	private int id;
	
	private String password;
	
	private String name;
	
	public Person(){
		
	}

	public Person(int id, String password, String name) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", name="
				+ name + "]";
	}
	
	
}
