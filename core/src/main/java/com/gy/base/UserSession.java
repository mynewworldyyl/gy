package com.gy.base;

import java.util.HashMap;
import java.util.Map;

import com.gy.entity.Account;

public class UserSession {

	private UserInfo userInfo = null;
	
	private Account account = null;
	
	public UserSession(UserInfo info) {
		this.userInfo = info;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}

	private Map<String,Object> parameters = new HashMap<String,Object>();
	
	public void setAttribute(String key,Object value){
		this.parameters.put(key, value);
	}
	
	public Object getAttribute(String key){
		return this.parameters.get(key);
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
