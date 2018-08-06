package com.gy.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.gy.base.CommonException;
import com.gy.base.Response;
import com.gy.base.UserContext;
import com.gy.base.id.ICacheIDGenerator;
import com.gy.entity.Account;
import com.gy.entity.VerificationCode;
import com.gy.im.services.ImManager;
import com.gy.mapper.IAccountDao;
import com.gy.mapper.IVerificationCodeDao;
import com.gy.service.AccountService;
import com.gy.usercenter.AccountCenterManager;
import com.gy.util.JsonUtils;
import com.gy.util.SecurityUtils;

@Component
public class AccountServiceImpl{

	@Autowired
	private IAccountDao userMapper;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private IVerificationCodeDao vcodemapper;
	
	@Autowired
	private AccountCenterManager acService;
	
	@Autowired
	private ImManager imManager;
	
	
	/*@Autowired
	private IUserDeviceDao userDeviceMapper;*/

	public boolean update(Account user) {
		setDefalutClient(user);
		userMapper.update(user);
		return true;
	}

	public String doLogin(String accountName,String pwd,HttpServletRequest req) {
		JSONObject result = this.defultResponseJson();
		Response resp = acService.login(accountName, pwd,req.getLocale());
		Account account = this.userMapper.selectByName(accountName,true);
		if(resp.isSuccess()){
			result.put("data", respJsonObject(account));
		} else {
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("errorMsg", resp.getMsg());
		}
		return result.toJSONString();
	}
	
	private JSONObject respJsonObject(Account account) {
		JSONObject json = new JSONObject();
		json.put("userName", account.getUserName());
		json.put("nickName", account.getNickName());
		json.put("actId", account.getId());
		json.put("email", account.getEmail());
		json.put("mobile", account.getMobile());
		json.put("homePhone", account.getHomePhone());
		json.put("homeAddr", account.getAddrLine1());
		json.put("iconUrl", account.getIconUrl());
		json.put("loginKey", UserContext.getCurrentACAccount().getLoginKey());
		return json;
	}
	
	public String logout() {
		UserContext.getAccount();
		Response resp = acService.logout(UserContext.getCurrentACAccount().getLoginKey());
		return JsonUtils.getInstance().toJson(resp, false);
	}
	
	public String isLogin(String loginKey) {
		Response resule = acService.isLogin(loginKey);
		resule.setObj(null);
		return JsonUtils.getInstance().toJson(resule, false);
	}
	
    public String registeAccount(String userName,String password,
    		String nickName,String iconUrl,String vcode,boolean isMo) {
		
		if (vcode == null || vcode.length() != 6) {
			throw new CommonException(
					"CheckCodeCannotBeNull");
		} else {
			// 未实现
			VerificationCode vc = vcodemapper.selectLast(userName,
					VerificationCode.TypeCode.Regist.value);
			if (vc == null || !vc.getCode().equals(vcode)) {
				throw new CommonException("CheckCodeNotMatch");
			}
		}

		if (StringUtils.isEmpty(userName)) {
			throw new CommonException("AccountNameCannotBeNull");
		}

		if (StringUtils.isEmpty(password)) {
			throw new CommonException("PasswordCannotBeNull");
		}

		Account user = null;
		try {
			user = this.userMapper.selectByName(userName,true);
		} catch (CommonException e) {
			
		}
		
		if(user != null && user.getRegisted()) {
        	throw new CommonException("AccountNameExist");
		}
		
		if(user == null) {
			user = this.userMapper.selectByName(userName,false);
		}
		
		if(user == null) {
			user = new Account();	
		}
		      
		user.setUserName(userName);
		user.setMobile(userName);
		user.setNickName(nickName);
		user.setPassword(SecurityUtils.md5(password));
		user.setTypeCode(AccountService.Type.Normal.name());
		user.setStatus(AccountService.Status.Normal.name());
		user.setIconUrl(iconUrl);

		this.validateUser(user);
		
		setDefalutClient(user);
		user.setRegisted(true);
		
		if(user.getId() == null) {
			user.setId(generator.getNumId(Account.class).longValue());			
			userMapper.save(user);
		} else {
			userMapper.update(user);
		}
		
		imManager.newGroup(null, user);
		
		JSONObject result = this.defultResponseJson();
		JSONObject resultData = new JSONObject();
		resultData.put("userName", userName);
		resultData.put("nickName", user.getNickName());
		resultData.put("id", user.getId());
		result.put("data", resultData);
	
		return result.toJSONString();
		
	}
	
    private void validateUser(Account user){
		if(StringUtils.isEmpty(user.getUserName())){
			throw new CommonException("AccountNameCannotBeNull");
		};
		
		StringUtils.isEmpty(user.getMobile());
		StringUtils.isEmpty(user.getPassword());
	}
	
	public String updatePwd(String userName,boolean isForget, String oldPwd, String newPwd,String vcode) {

		JSONObject result = this.defultResponseJson();

		//Account user = UserContext.getAccount();
		
		Account user = this.userMapper.selectByName(userName,true);
		
		if(vcode == null || vcode.length() != 6){
			throw new CommonException("CheckCodeCannotBeNull");
		} else {
			VerificationCode vc = vcodemapper.selectLast(userName, 
					VerificationCode.TypeCode.ResetPwd.value);
			if(vc == null || !vc.getCode().equals(vcode)){
				throw new CommonException("CheckCodeNotMatch");
			}
		}
		
		if( AccountService.Type.Root.name().equals(user.getTypeCode()) 
				  || AccountService.Type.Admin.name().equals(user.getTypeCode())){
			throw new CommonException("NoPermission");
		}
		
		//Account user = userService.selectByName(userName);
		if (user != null) {
			if(isForget){
				String md5Pwd = SecurityUtils.md5(newPwd);
				user.setPassword(md5Pwd);
				this.update(user);
			}else{
				String md5Pwd = SecurityUtils.md5(oldPwd);
				if(user.getPassword().equals(md5Pwd) || user.getPassword().equals(oldPwd)){
					md5Pwd = SecurityUtils.md5(newPwd);
					user.setPassword(md5Pwd);
					this.update(user);
				} else {
					throw new CommonException("PasswordError");
				}
			}
		}	
		return result.toJSONString();
	
	}

	public JSONObject defultResponseJson(){
		JSONObject json = new JSONObject();
		json.put("success", true);
		return json;
	}

	private void setDefalutClient(Account acct) {
		acct.setClient(clientManager.getDefaultClient());
	}
	
}
