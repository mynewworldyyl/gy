package com.gy.base;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.base.Response;
import com.gy.base.UserContext;
import com.gy.usercenter.AccountCenterManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class AbstractCmtyTestCase {

	@Autowired
	private AccountCenterManager acManager;
	
	public void setAdminZJUP() {
		//a94acf24fb89a3aa3c5107d85c059ca3

		String cid = "a94acf24fb89a3aa3c5107d85c059ca3";
		//王建飞
		//Response resp = this.acManager.login("18718929527","123456",null);
		//王坤
		Response resp = this.acManager.login("18566206828","123456",null);		
		//Response resp = this.acManager.login("13266878991","888888",null);
		//Response resp = this.acManager.login("18718929527","123456",null);
		
		UserContext.getCurrentACAccount().setMsgClientId(cid);
		Assert.assertTrue(resp.isSuccess());
		
		Assert.assertNotNull(UserContext.getCurrentACAccount());
		Assert.assertNotNull(UserContext.getAccount());
		Assert.assertNotNull(UserContext.getCurrentClientId());
		Assert.assertNotNull(UserContext.getCurrentUserId());
		Assert.assertNotNull(UserContext.getLocale());
		
	}
}
