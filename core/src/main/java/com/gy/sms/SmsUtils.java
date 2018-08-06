package com.gy.sms;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;

import org.apache.commons.lang.LocaleUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;

public class SmsUtils {
	
	private final static Logger LOG = Logger.getLogger(SmsUtils.class);
			
	//ws访问地址
	private final static String url = "http://ws.iems.net.cn/GeneralSMS/ws/SmsInterface?wsdl";
	private final static String userName;
	private final static String pwd;
	private final static String managerMobile;
	
	private static SmsInterfaceServiceLocator smsServiceLocator;
	static {
		smsServiceLocator = new SmsInterfaceServiceLocator();
		smsServiceLocator.setSmsInterfaceEndpointAddress(url);
		managerMobile = "13602626960"; //Env.getProperty("Monitor.Sms.Accout.Manager.Mobile");
		userName = "68791:admin";//Env.getProperty("Sms.Account.UserName");
		pwd = "45246506";//Env.getProperty("Sms.Account.pwd");
	}
	
	public static void sendSms(String to, String text){
		try {
			String resp = smsServiceLocator.getSmsInterface().clusterSend(userName, pwd, "", to, text, "", "false");
			LOG.info("resp = " + resp);
			
			SAXReader saxReader = new SAXReader();  
	        Document doc = saxReader.read(new ByteArrayInputStream(resp.getBytes("UTF-8")));  
	        Element root = doc.getRootElement();  
	        Element codeE = root.element("code");  
	        
	        if(codeE != null && !"1000".equalsIgnoreCase(codeE.getText())){
	        	LOG.error("Send sms failed: "+resp);
	        }else{
	        	LOG.info("Send sms To:[" + to + "] ,Content:[" + text + "] success");
	        }
	        
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error("ERROR:" + ex.getMessage());
		}
	}
	
	public static JSONObject getAccountInfo(){
		JSONObject result = new JSONObject();
		try {
			String resp = smsServiceLocator.getSmsInterface().getUserInfo(userName, pwd);
			LOG.info("resp = " + resp);
			
			result.put("success", true);
			
			SAXReader saxReader = new SAXReader();  
	        Document doc = saxReader.read(new ByteArrayInputStream(resp.getBytes("UTF-8")));  
	        Element root = doc.getRootElement();  
	        
	        Element element = root.element("code");  
	        result.put("code", element.getText());
	        
	        element = root.element("balance");  
	        result.put("balance", element.getText());
	        
	        element = root.element("smsPrice");  
	        result.put("smsPrice", element.getText());
	        
	        element = root.element("voicePrice");  
	        result.put("voicePrice", element.getText());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("success", false);
		}
        
        LOG.info("json string ： " + result.toJSONString());
		return result;
	}
	
	public static void monitorAccout(){
		try{
			JSONObject result = SmsUtils.getAccountInfo();
			if(result.getBoolean("success") && "1000".equals(result.getString("code"))){
				 BigDecimal balance = result.getBigDecimal("balance");
				 if(balance.compareTo(new BigDecimal(10)) < 0){
					 String minBalance ="10"; // Env.getProperty("Monitor.Sms.Account.MinBalance");
					 String tmp = "sms manager account:{0}";
					 SmsUtils.sendSms(managerMobile, MessageFormat.format(tmp, minBalance));
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}
	
	public static void ayncMonitorAccout(){
		MonitorRunnable m = new MonitorRunnable();
		Thread t = new Thread(m);
		t.start();
	}
	
	protected static class MonitorRunnable implements Runnable{
		public void run() {
			 SmsUtils.monitorAccout();
		}
	}
}
