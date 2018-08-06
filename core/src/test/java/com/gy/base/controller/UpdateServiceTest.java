package com.gy.base.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.base.AbstractCmtyTestCase;
import com.gy.base.file.DbFile;
import com.gy.bs.controller.UpdateService;
import com.gy.entity.VerificationCode;
import com.gy.web.servlet.file.DownloadServlet;

public class UpdateServiceTest  extends AbstractCmtyTestCase{

	@Autowired
	private UpdateService updateService;
	
	
	@Test
	@Transactional
	@Rollback(false)
	public void testAndroidInfo() {
		
		setAdminZJUP();
		String result = updateService.latestInfo(DbFile.ContentType.APK.name());
		Assert.assertNotNull(result);
		
		JSONObject data = JSON.parseObject(result);
	}
	
	
	
}
