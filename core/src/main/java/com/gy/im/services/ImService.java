package com.gy.im.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.im.models.Group;
import com.gy.usercenter.AccountCenterManager;
import com.gy.util.Utils;

public class ImService {

	private Logger logger = Logger.getLogger(this.getClass());
	private static String FAIL = "fail";
	private static String SUCCESS = "success";
	
	@Autowired
	private AccountCenterManager longinManager;
	
/*	@Autowired
	private IUserManager userManager;*/
	
	@GET
	@Path("/register")
	//@Produces(MediaType.APPLICATION_JSON)
	public String createUser(@QueryParam("username") String username) {
		
		return SUCCESS;
	}
	
	@GET
	@Path("/friends")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGroups(@QueryParam("userId") String userId) {
		
		return null;
	}
	
	@GET
	@Path("/unregister")
	//@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(@QueryParam("username") String username) {
		
		return SUCCESS;
	}

}
