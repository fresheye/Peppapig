package com.example.ilzxm.peppapig;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Users extends BmobObject{
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String password;
	private BmobFile image;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setImage(BmobFile image) {
		this.image = image;
	}

	public BmobFile getImage() {
		return image;
	}

}
