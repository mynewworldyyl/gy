package com.gy.usercenter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.base.CommonException;
import com.gy.base.Response;
import com.gy.base.UserContext;
import com.gy.bs.controller.BaseController;
import com.gy.entity.Account;
import com.gy.service.impl.AccountServiceImpl;
import com.gy.util.Utils;


@Path("/user")
@Component
@Scope("singleton")
@Qualifier("user")
public class UserController extends BaseController {

	private final static Logger LOG = Logger.getLogger(UserController.class);
	
	@Autowired
	private AccountServiceImpl userService; 
		
	@POST
	@Path("/regist")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String registeAccount(
			@FormParam("accountName") String userName,
			@FormParam("password") String password,
			@FormParam("nickName") String nickName,
			@FormParam("iconUrl") String iconUrl,
			@FormParam("VerificationCode") String vcode) {
		return userService.registeAccount(userName,password,nickName,iconUrl,vcode,false);
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String login(@FormParam("accountName") String accountName,
			@FormParam("pwd") String pwd,@FormParam("cid") String msgClientId,
			@Context HttpServletRequest req) {
		String resule = userService.doLogin(accountName,pwd,req);
		if(msgClientId != null && !"".equals(msgClientId.trim())) {
			updateCid(msgClientId);
		}
		return resule;
	}
	
	@POST
	@Path("/isLogin")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String isLogin(@FormParam("loginKey") String loginKey) {
		return userService.isLogin(loginKey);
	}
	
	
	@POST
	@Path("/updateCid")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String updateCid(@FormParam("cid") String cid) {
		UserContext.getCurrentACAccount().setMsgClientId(cid);
		return Utils.getInstance().getResponse(null, true, Response.SUCCESS);
	}
	
	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String logout() {
		return userService.logout();
	}
	
	@POST
	@Path("/update/pwd")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public  String updatePwd(
			@FormParam("userName") String userName,
			@FormParam("isForget") boolean isForget,
			@FormParam("oldPwd") String oldPwd,
			@FormParam("newPwd")String newPwd, 
			@FormParam("VerificationCode") String vcode) {
		return userService.updatePwd(userName,isForget, oldPwd, newPwd, vcode);
	}
	
	@POST
	@Path("/update/act")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public  String updateAct(
			@FormParam("key") String name,
			@FormParam("value") String value) {
		Account act = UserContext.getAccount();
		if(value == null || "".equals(value)) {
			throw new CommonException("ActFieldNotFound",name);
		}
		value = value.trim();
		if("nickName".equals(name) && !value.equals(act.getNickName())) {
			act.setNickName(value);
		}else if("email".equals(name) && !value.equals(act.getEmail())) {
			act.setEmail(value);
		}else if("homePhone".equals(name) && !value.equals(act.getHomePhone())) {
			act.setHomePhone(value);
		}else if("mobile".equals(name) && !value.equals(act.getMobile())) {
			act.setMobile(value);
		}else if("homeAddr".equals(name) && !value.equals(act.getAddrLine1())) {
			act.setAddrLine1(value);
		}else if("iconUrl".equals(name) && !value.equals(act.getIconUrl())) {
			act.setIconUrl(value);
		}else {
			throw new CommonException("ActFieldNotFound",name);
		}
		userService.update(act);
		return Utils.getInstance().getResponse(act, true, Response.SUCCESS);
			
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String testService(@QueryParam("userName") String userName) {
		return "success" + userName;
	}
	
}
