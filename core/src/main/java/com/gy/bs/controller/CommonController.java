package com.gy.bs.controller;

import java.util.Random;

import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.gy.base.CommonException;
import com.gy.base.i18n.I18NUtils;
import com.gy.entity.Account;
import com.gy.entity.VerificationCode;
import com.gy.mapper.IAccountDao;
import com.gy.service.VerificationCodeService;
import com.gy.service.impl.ClientManager;
import com.gy.sms.SmsUtils;

//@Controller("commonController")
//@RequestMapping("/comm")

@Path("/comm")
@Component
@Scope("singleton")
@Qualifier("comm")
public class CommonController extends BaseController{

	@Autowired
	private VerificationCodeService vcodeService;
	
	@Autowired
	private IAccountDao userMapper;
	
	@Autowired
	private ClientManager clientManager;
	
	private String generateCode(String btype){
		int max=999999;
        int min=100000;
        Random random = new Random();

        int code = random.nextInt(max)%(max-min+1) + min;
        
		return String.valueOf(code);
	}
	
	//@ResponseBody
	@GET
	@Path("/hb")
	public String hearbeat(){
		return "success";
	}
	
	//@RequestMapping(value = "/sms/generatecode", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	//@ResponseBody
	@POST
	@Path("/sms/generatecode")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation=Propagation.REQUIRED)
	public String getCode(/*@RequestParam(value = "btype")String btype,
	@RequestParam(value = "mobile")String mobile*/
			@FormParam("btype") String btype,@FormParam("mobile") String mobile){
		
		JSONObject result = this.defultResponseJson();

		String code = this.generateCode(btype);
		if(!VerificationCode.TypeCode.Regist.value.equals(btype)
				&& !VerificationCode.TypeCode.ResetPwd.value.equals(btype)){
			throw new CommonException("OperationError");
		}
		
		Account user = null;
		try {
			user = (Account)this.userMapper.getEntityManager().createNamedQuery("AccountSelectByMobile")
					.setParameter("mobile", mobile)
					.getSingleResult();
		} catch (NoResultException e) {
			
		} 
		
		if(VerificationCode.TypeCode.Regist.value.equals(btype)){
			if(user != null){
				throw new CommonException("AccountHaveBeenRegister",mobile);
			}
		}
		
		if(VerificationCode.TypeCode.ResetPwd.value.equals(btype)){
			if(user == null){
				throw new CommonException("AccountNameNotRegister",mobile);
			}
		}
		
		
		VerificationCode record = new VerificationCode();
		record.setClient(clientManager.getDefaultClient());
		record.setCode(code);
		record.setMobile(mobile);
		record.setStatus("1001");
		record.setTypeCode(btype);
		vcodeService.insert(record);
		
		String msg = I18NUtils.getInstance().getString("checkcode.template",code); 
		//SmsUtils.sendSms(mobile, msg);
		
		SmsUtils.ayncMonitorAccout();
		
        JSONObject data  = new JSONObject();
		data.put("codeId", record.getId());
		data.put("code", record.getCode());
		this.putData(result, data);
	
		return result.toJSONString();
	}

}
