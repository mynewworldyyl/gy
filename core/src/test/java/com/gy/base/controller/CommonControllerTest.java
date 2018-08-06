package com.gy.base.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.gy.base.AbstractCmtyTestCase;
import com.gy.base.id.CacheBaseIDManager;
import com.gy.bs.controller.CommonController;
import com.gy.entity.VerificationCode;
import com.gy.im.models.Channel;

public class CommonControllerTest  extends AbstractCmtyTestCase{

	@Autowired
	private CommonController commonController;
	
	@Autowired
	private CacheBaseIDManager generator;
	
	@Test
	@Transactional
	@Rollback(false)
	public void testGetCode() {
		setAdminZJUP();
		String code = this.commonController.getCode(
				VerificationCode.TypeCode.Regist.value, "13266878991");
		Assert.assertNotNull(code);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testGetId() {
		//setAdminZJUP();
	    String id = generator.getStringId(Channel.class);
		Assert.assertNotNull(id);
		System.out.println("");
	}
	
}
