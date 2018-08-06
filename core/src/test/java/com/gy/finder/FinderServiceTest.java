package com.gy.finder;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.gy.base.AbstractCmtyTestCase;
import com.gy.base.email.MailService;
import com.gy.base.id.CacheBaseIDManager;

public class FinderServiceTest  extends AbstractCmtyTestCase{

	@Autowired
	private FacePPService fppService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private CacheBaseIDManager generator;
	

	@Test
	@Transactional
	@Rollback(false)
	public void testStartWork() {
		fppService.startWork(new long[]{512});
		try {
			Thread.sleep(1000*60*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testStartCollWork() {
		fppService.startWork(new long[]{401});
		try {
			Thread.sleep(1000*60*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSendEmail() {
		mailService.sendSimpleMail("ylye@digitnexus.com","测试邮件", "这是测试邮件，你收到了吗?");
		//mailService.sendSimpleMail("378862956@qq.com","测试邮件", "这是测试邮件，你收到了吗?");
		//mailService.sendSimpleMail("mynewworldyyl@gmail.com","测试邮件", "这是测试邮件，你收到了吗?");
	}
	
}
