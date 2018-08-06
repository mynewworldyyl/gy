package com.gy.base;

import java.util.Locale;

import com.gy.base.Client;
import com.gy.base.UserInfo;
import com.gy.entity.Account;

public class MockUserInfo extends UserInfo{

	public MockUserInfo(Client loginClient,Account account,Locale locale) {
		super(loginClient,account,locale);
	}

	@Override
	public Account getAccount() {
		return null;
	}

	@Override
	public Client getLoginClient() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
