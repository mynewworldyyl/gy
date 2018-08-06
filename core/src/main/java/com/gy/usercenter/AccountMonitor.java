package com.gy.usercenter;

import org.springframework.beans.factory.annotation.Autowired;

public class AccountMonitor {

	@Autowired
	private AccountCenterManager acManager;
	
	public void monitor() {
		acManager.checkTimeout();
	}
}
