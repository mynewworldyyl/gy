package com.gy.usercenter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.base.CommonException;
import com.gy.base.Response;
import com.gy.base.UserContext;
import com.gy.base.i18n.I18NUtils;
import com.gy.base.id.ICacheIDGenerator;
import com.gy.entity.Account;
import com.gy.mapper.IAccountDao;
import com.gy.service.impl.ClientManager;
import com.gy.util.SecurityUtils;

@Component
public class AccountCenterManager {
	
	private final static Logger logger = Logger.getLogger(AccountCenterManager.class);
	
	private Map<String,ACAccount> accounts = Collections.synchronizedMap(new HashMap<String,ACAccount>());// new HashMap<String,ACAccount>();
	
	@Autowired
	private IAccountDao userMapper;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	@Autowired
	private ClientManager clientManager;
	
	public static long maxTimeout = 3*60*60*1000;
	
	//private boolean isUserCenterServer = true;
	public ACAccount getACAccount(String un) {
		if(un == null || "".equals(un.trim())) {
			return null;
		}
		for(ACAccount aca : this.accounts.values()) {
			if(un.equals(aca.getAccountName())) {
				return aca;
			}
		}
		return null;
	}
	
	public Response login(String un, String pw, Locale l) {
		if(l == null) {
			l = Locale.getDefault();
		}
		
		if(pw == null) {
			throw new CommonException("PasswordCannotBeNull");
		}
		
		if(un == null) {
			throw new CommonException("AccountCannotBeNull");
		}
		
		Response resp = new Response(true);
		Account account = this.userMapper.selectByName(un,true);
		String md5Pwd = SecurityUtils.md5(pw);
		
		if(account == null || (!account.getPassword().equals(md5Pwd) 
				&& !account.getPassword().equals(pw))){
			throw new CommonException("AccountOrPwError");
		}
		
		ACAccount aca = null;
		if(this.isCurAccountLogin(un)) {
			String loginKey = this.currentAcaAccount(un).getLoginKey();
			aca = accounts.get(loginKey);
			//this.logout(this.currentAcaAccount(un).getLoginKey());
			/*resp.setSuccess(false);
			resp.setMsg(I18NUtils.getInstance().getString("HaveBeenLogin"));
			return resp;*/
		}else {
			aca = new ACAccount();
			aca.setLoginKey(this.generator.getStringId(ACAccount.class));
			aca.setAccountName(account.getUserName());
			aca.setAcct(account);
			aca.setL(l);
			aca.setClientId(account.getClient().getId());
			aca.setAcct(account);
			accounts.put(aca.getLoginKey(), aca);
		}
		
		UserContext.init(aca);
		resp.setLoginKey(aca.getLoginKey());
		
		return resp;
	}
	
	private ACAccount currentAcaAccount(String un) {
		if(un == null || "".equals(un.trim())) {
			return null;
		}
		ACAccount acac = null;
		un = un.trim();
		for(ACAccount aca : this.accounts.values()) {
			if(un.equals(aca.getAccountName())) {
				acac = aca;
				break;
			}
		}
		return acac;
	}
	
	private boolean isCurAccountLogin(String un) {
		return currentAcaAccount(un) != null;
	}
	
	private boolean isKeyLogin(String key) {
	    return this.accounts.get(key) != null;	
	}

	
	public Response logout(String key) {
		Response resp = new Response(false);
		if(!this.isKeyLogin(key)) {
			resp.setMsg(I18NUtils.getInstance().getString("NotLogin"));
			return resp;
		}
		resp.setSuccess(true);
		resp.setLoginKey(key);
		accounts.remove(key);
		return resp;
	}

	
	public Response isLogin(String loginKey) {
		if(/*isCurAccountLogin(loginKey)*/isKeyLogin(loginKey)) {
			Response resp = new Response(true);
			ACAccount aca = this.accounts.get(loginKey);
			resp.setLoginKey(aca.getLoginKey());
			resp.setData(aca.getAccountName());
			resp.setObj(aca);
			return resp;
		}
		return new Response(false);
	}
	
	public void checkTimeout() {
		long time = System.currentTimeMillis();
		Map<String,ACAccount> ass = new HashMap<String,ACAccount>();
		for(ACAccount aca : this.accounts.values()) {
			long divtime = time - aca.getLastertime();
			if(divtime > maxTimeout) {
				logger.info("checkTimeout: "+aca.getAccountName());
				ass.put(aca.getLoginKey(), aca);
				//this.logout(aca.getId());
			}
		}
		
		for(ACAccount aca : ass.values()) {
			this.logout(aca.getLoginKey());
		}
		ass.clear();
		ass=null;
	}

	public Account createNotRegistedAccount(String userName) {
		Account act = new Account();
		act.setUserName(userName);
		act.setMobile(userName);
		act.setRegisted(false);
		act.setClient(clientManager.getDefaultClient());
		act.setId(this.generator.getNumId(Account.class).longValue());
		this.userMapper.save(act);
		return act;
	}

}
