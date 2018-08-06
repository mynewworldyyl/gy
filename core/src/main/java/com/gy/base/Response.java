package com.gy.base;

import java.io.Serializable;

public class Response implements Serializable{

	public static final String 	SUCCESS = "success";
	public static final String 	FAIL = "fail";
	
	private boolean success = true;
	private String msg = "";
	private String loginKey = "";
	
	private String classType = "";
	
	private String data = null;
	
	private Object obj = null; 

	public Response() {
		this(true);
	}
	
	public Response(boolean f) {
		this.success = f;
	}
	
	public Response(boolean f,String msg) {
		this(f);
		this.msg=msg;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
