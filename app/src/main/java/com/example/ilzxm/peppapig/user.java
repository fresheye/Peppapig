package com.example.ilzxm.peppapig;

import cn.bmob.v3.BmobObject;

public class user extends BmobObject{
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
