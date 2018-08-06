package com.gy.base;

import java.util.Locale;

import com.gy.entity.Account;


public class UserInfo {

    private Account account;
    
    private Client loginClient;
    
    private Locale locale;
    
    public UserInfo(Client loginClient,Account account,Locale locale) {
    	this.account = account;
    	this.loginClient = loginClient;
    	this.locale = locale;
    }

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Client getLoginClient() {
		return loginClient;
	}

	public void setLoginClient(Client loginClient) {
		this.loginClient = loginClient;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

    
}
