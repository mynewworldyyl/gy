package com.gy.base;

import com.gy.entity.Account;

public class MockAccount extends Account{

	public MockAccount() {
		this.setTypeCode(Account.TYPE_ADMIN);
		this.setId(0l);
	}
}
