package com.gy.usercenter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Id;

import org.apache.log4j.Logger;

import com.gy.base.id.IDStrategy;

@IDStrategy(prefixValueLen=0)
public class ACAccount {
	private final static Logger logger = Logger.getLogger(ACAccount.class);
	
	@Id
	private String loginKey;
	
	private String accountName="";
	
	private String clientId;
	
	private String msgClientId = null;
	
	private Locale l;
	
	//private String pwd;
	
	private Object acct = null;
	
	private Map<String,Object> params = new HashMap<String,Object>();
	
	private long lastertime = System.currentTimeMillis();

	public void setAttribute(String key, Object value) {
		this.params.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return this.params.get(key);
	}
	
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Locale getL() {
		return l;
	}

	public void setL(Locale l) {
		this.l = l;
	}

	

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Object getAcct() {
		return acct;
	}

	public void setAcct(Object acct) {
		this.acct = acct;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public String getMsgClientId() {
		return msgClientId;
	}

	public void setMsgClientId(String msgClientId) {
		this.msgClientId = msgClientId;
	}

	@Override
	public int hashCode() {
		return this.loginKey.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}else if(obj == null || !(obj instanceof ACAccount)) {
			return false;
		}
		ACAccount a = (ACAccount)obj;
		if(this.loginKey.equals(a.loginKey)) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ACAccount a = (ACAccount)super.clone();
		return a;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID=").append(this.loginKey)
		.append(", key=").append(this.loginKey)
		.append(", accountName=").append(this.accountName);
		return sb.toString();
	}

	public long getLastertime() {
		return lastertime;
	}

	public void setLastertime(long lastertime) {
		this.lastertime = lastertime;
	}
	
	public void reflesh() {
		//logger.info("Refresh: "+this.getAccountName());
		this.lastertime = System.currentTimeMillis();
	}
		
}
