package com.gy.base;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.gy.base.Client;
import com.gy.base.ClientType;
import com.gy.base.i18n.I18NUtils;
import com.gy.base.id.CacheBaseIDManager;
import com.gy.entity.Account;
import com.gy.mapper.IAccountDao;
import com.gy.mapper.imp.ClientDaoImpl;
import com.gy.mapper.imp.CommonDaoImpl;
import com.gy.sms.SmsUtils;

public class InitData extends AbstractCmtyTestCase{

/*	@Autowired
	private ClientTypeDaoImpl ctDao;
	
	@Autowired
	private DeptDaoImpl deptDao;
	
	@Autowired
	private ClientDaoImpl clientDao;*/
	
	@Autowired
	private CacheBaseIDManager generator;
	
	@Autowired
	private ClientDaoImpl clientDao;
	
	@Autowired
	private CommonDaoImpl baseDao;
	
	@Autowired
	private IAccountDao acctDao;
	
/*	@Autowired
	private PermManager permManager;*/
	
	@Test
	@Transactional
	@Rollback(false)
	public void initMain(){
		 initClientType();
		 addUser();
		//initPermission();
		//initDept();
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void initClientType() {
		
		Date now = new Date();
		 
		ClientType ct = new ClientType();
		ct.setName(ClientType.Admin);
		ct.setTypeCode(ClientType.Admin);
		ct.setDescription(ClientType.Admin);
		ct.setId(generator.getStringId(ClientType.class));
		ct.setCreatedBy(null);
		ct.setCreatedOn(now);
		ct.setUpdatedBy(null);
		ct.setUpdatedOn(now);
		this.baseDao.save(ct);
		
        Client c = new Client();
        String name = "Defalult";
    	c.setDescription(name);
    	c.setId(generator.getStringId(Client.class));
    	c.setName(name);
    	c.setParent(null);
    	c.setRemark(name);
    	c.setSubClients(null);
    	c.setTypecode(ct);
    	c.setUpdatedOn(now);
    	c.setCreatedOn(now);
    	this.clientDao.save(c);
    	
    	/*Account a = new Account();
    	
    	a.setAddrLine1("");
    	a.setAddrLine2("");
    	a.setClient(c);
    	a.setDescription("13266878992");
    	a.setEmail("admin@hy.com");
    	a.setHomePhone("13266878992");
    	a.setIconUrl("");
    	a.setId(generator.getNumId(Account.class).longValue());
    	a.setMobile("");
    	a.setNickName("admin");
    	a.setOfficePhone("");
    	a.setPassword("13266878992");
    	a.setRemark("admin");
    	a.setStatus(Account.STATU_ACTIVE);
    	a.setTypeCode(Account.TYPE_ADMIN);
    	a.setUserName("13266878992");
    	this.acctDao.save(a);*/
    	
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void addUser() {
		
		Client c = clientDao.getClientByName("Defalult");
        Account a = new Account();
    	
    	a.setAddrLine1("");
    	a.setAddrLine2("");
    	a.setClient(c);
    	a.setDescription("admin");
    	a.setEmail("13266878992@hy.com");
    	a.setHomePhone("");
    	a.setIconUrl("");
    	a.setId(generator.getNumId(Account.class).longValue());
    	a.setMobile("13266878992");
    	a.setNickName("yyl");
    	a.setOfficePhone("");
    	a.setPassword("888888");
    	a.setRemark("13266878991");
    	a.setStatus(Account.STATU_ACTIVE);
    	a.setTypeCode(Account.TYPE_ADMIN);
    	a.setUserName("13266878992");
    	this.acctDao.save(a);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void getID() {
		
		String id = generator.getStringId(Client.class);
		System.out.println(id);
		
		id = generator.getStringId(Client.class);
		System.out.println(id);
		
		long strid = generator.getNumId(Account.class).longValue();
		System.out.println(strid);
		
		/*strid = generator.getNumId(Account.class).longValue();
		System.out.println(strid);*/
	}
	
	@Test
	public void testSendMsg() {
		
		String msg = I18NUtils.getInstance().getString("checkcode.template","155391"); 
		//SmsUtils.sendSms("18718929527", msg);
		SmsUtils.sendSms("15818790696", msg);
		//SmsUtils.sendSms("13266878991", msg);
		//SmsUtils.sendSms("13602626960", msg);
	}
	
}
