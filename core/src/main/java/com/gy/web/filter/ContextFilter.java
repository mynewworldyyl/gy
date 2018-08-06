package com.gy.web.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.gy.base.CommonException;
import com.gy.base.Response;
import com.gy.base.SpringContext;
import com.gy.base.UserContext;
import com.gy.base.i18n.I18NUtils;
import com.gy.usercenter.ACAccount;
import com.gy.usercenter.AccountCenterManager;
import com.gy.util.JsonUtils;

//@WebFilter(urlPatterns = { "/*" }, asyncSupported = true, description = "Context Filter")
public class ContextFilter implements Filter {
	private final static Logger logger = Logger.getLogger(ContextFilter.class);
	
	private static final String[] escapeUrl = {
		"/user/login","/user/regist","/user/test","/comm/sms/generatecode",
		"/user/update/pwd"
	};
	
	private AccountCenterManager acManager;
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		Response res = null;
		try {
			synchronized(escapeUrl){
				if(this.acManager == null) {
					this.acManager = SpringContext.getContext().getBean(AccountCenterManager.class);
					if(this.acManager == null) {
						return;
					}
				}
			}
			
			boolean f = true;
			if(this.isNeedInitThreadLocal(req)) {
				f = initThreadLocal(req,resp);
			}
			
			if(f) {
				chain.doFilter(request, response);
			}
				
		}catch(Throwable exce) {
			ACAccount aca = UserContext.getCurrentACAccount();
			
			res = new Response(false);
            if(aca != null) {
			    res.setLoginKey(aca.getLoginKey());	
			}
			if(exce instanceof CommonException) {
				CommonException cd = (CommonException)exce;
				res.setMsg(I18NUtils.getInstance().getString(cd.getI18nKey(),cd.getArgs()));
			}else if(exce instanceof InvocationTargetException) {
				InvocationTargetException e = (InvocationTargetException)exce;
				Throwable ex = e.getTargetException();
				if(ex instanceof CommonException) {
					CommonException cd = (CommonException)ex;
					res.setMsg(I18NUtils.getInstance().getString(cd.getI18nKey(),cd.getArgs()));
				}else {
					res.setMsg(I18NUtils.getInstance().getString("NotExpectedException"));
					//res.setMsg(I18NUtils.getInstance().getString(exce.getMessage()== null ? "NotExpectedException": exce.getMessage()));
				}
			}else {
				res.setMsg(I18NUtils.getInstance().getString("NotExpectedException"));
				//res.setMsg(I18NUtils.getInstance().getString(exce.getMessage()== null ? "NotExpectedException": exce.getMessage()));
			}
			response.setContentType(MediaType.APPLICATION_JSON);
			resp.getWriter().append(JsonUtils.getInstance().toJson(res,false));
			//resp.flushBuffer();
			if( exce instanceof CommonException) {
				CommonException ce = (CommonException)exce;
				if(!"NotLogin".equals(ce.getI18nKey())) {
					logger.error(res.getMsg(),exce);
				}
			}else {
				logger.error(res.getMsg(),exce);
			}
			
			UserContext.releaseContext();
		}
	
	}
	
	private boolean initThreadLocal(HttpServletRequest req,HttpServletResponse resp) {
		String loginKey = req.getHeader("loginKey");
		
		/*String accountName = req.getParameter("accountName");
		if(accountName == null || "".equals(accountName.trim())) {
			accountName = req.getParameter("userName");
		}
		
		if(accountName == null || "".equals(accountName.trim())) {
			accountName = req.getHeader("accountName");
		}
		
		if(accountName == null || "".equals(accountName.trim())) {
			accountName = req.getHeader("userName");
		}
		*/
		if(loginKey == null || "".equals(loginKey.trim())) {
			throw new CommonException("MissingLoginInfo");
		}
		
		Response r = this.acManager.isLogin(loginKey);
		if(!r.isSuccess()) {
			throw new CommonException("NotLogin",loginKey);
		}
		ACAccount aca = (ACAccount)r.getObj();
		aca.reflesh();
		UserContext.init(aca);
		return true;
	}

	private boolean isNeedInitThreadLocal(HttpServletRequest req) {
		boolean needinit = true;
		String url = req.getPathInfo();
		if(url == null) {
			return false;
		}
		for(String u : escapeUrl ) {
			if(url.indexOf(u) != -1) {
				needinit = false;
				break;
			}
		}
		return needinit;
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void destroy() {

	}
}
