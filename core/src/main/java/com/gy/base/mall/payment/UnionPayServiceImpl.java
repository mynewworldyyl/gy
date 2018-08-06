package com.gy.base.mall.payment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.gy.base.CommonException;
import com.gy.base.id.ICacheIDGenerator;
import com.gy.base.mall.Order;
import com.gy.base.mall.OrderDao;
import com.gy.base.mall.Payment;
import com.gy.base.mall.PaymentDao;
import com.gy.base.mall.Payment.Typecode;
import com.gy.service.IPayService;

@Component("unionPayServiceImpl")
public class UnionPayServiceImpl implements IPayService {

	private final static Logger logger = Logger.getLogger(UnionPayServiceImpl.class);
	
	private static final String ID = "unionPayService";
	
	private static final String UNION_DATETIME_FORMAT="yyyyMMddHHmmss";
	
	//银联技术给我们提供的商户密钥		
	//银联技术给我们提供的商户密钥
	@Value("#{configProperties['union.security.key']}")
	private String key = null;
	
	//商户的银联账号
	@Value("#{configProperties['union.mer.id']}")
	private String merId = null;
	
	//支付成功后银联的回调地址
	@Value("#{configProperties['union.mer.back.end.url']}")
	private String backendUrl = null;	
	
	//支付成功后银联回调的前段地址,可不填
	@Value("#{configProperties['union.mer.front.end.url']}")
	private String frontendUrl = null;
	
	//版本号
	@Value("#{configProperties['union.version']}")
	private String version = "1.0.0";
	
	//编码
	@Value("#{configProperties['union.charset']}")
	private String charset = "UTF-8";
	
	//加密方法
	@Value("#{configProperties['union.sign.method']}")
	private String signMehtod = "MD5";
	
	//处理交易请求的银联地址
	@Value("#{configProperties['union.upmp.trade.url']}")
	private String tradeUrl = null;
	
	//查询交易的银联地址
	@Value("#{configProperties['upmp.query.url']}")
	private String queryUrl = ""; 
	
	//银联提供的测试账户
	@Value("#{configProperties['union.card']}")
	private String card = null;
	
	//银联提供的测试密码
	@Value("#{configProperties['union.password']}")
	private String password = "";
	
	@Value("#{configProperties['http.timeout']}")
	private int timeout = 0;
	
	@Value("#{configProperties['connectionTimeout']}")
	private int connectionTimeout;
	
	@Value("#{configProperties['union.public.keyfile']}")
	private String publicKeyFilePath = null;
	
	@Value("#{configProperties['union.private.keyfile']}")
	private String privateKeyFilePath = null;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	@Autowired
	private PaymentDao payDao;
	
	@Autowired
	private OrderDao orderDao;
	
	private  HttpClient httpClient = null;
	
	private boolean isInit = false;
	
	private SecureLink pubLink;
	private SecureLink priLink;
	
	@SuppressWarnings("deprecation")
	private void  init() {
		 httpClient = new DefaultHttpClient();
         HttpParams httpParams = httpClient.getParams();
         HttpProtocolParams.setContentCharset(httpParams, this.charset);
         HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout);
         HttpConnectionParams.setSoTimeout(httpParams, timeout);
         
         PrivateKey pubKey = new PrivateKey();
         boolean flag = pubKey.buildKey(this.merId, 0, publicKeyFilePath);
         if(!flag) {
        	 logger.error("fail to init public key"+publicKeyFilePath);
        	 throw new CommonException("SystemError");
         }
         pubLink =  new SecureLink(pubKey);
         
         PrivateKey priKey = new PrivateKey();
         flag = priKey.buildKey(this.merId, 0, privateKeyFilePath);
         if(!flag) {
        	 logger.error("fail to init private key"+privateKeyFilePath);
        	 throw new CommonException("SystemError");
         }
         priLink =  new SecureLink(priKey);
         
         isInit=true;
	}

	
	@Override
	public String getID() {
		return ID;
	} 
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void savePayment(Payment pm) {
		this.payDao.save(pm);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void updatePayment(Payment pm) {
		this.payDao.update(pm);
	}
	
	@Override
	public void goodRejected(Order order) {
		 Payment payment = this.payDao.getPaymentByOrderNum(order.getOrderNum());
		 if(payment == null) {
			logger.error("Order not fount for payment notify: ");
			logger.error("orderNum="+order.getOrderNum());
			throw new CommonException("PaymentNotFound",order.getOrderNum());
		 }
		 payment.setStatus(Payment.Status.GOOD_REJECTING.name());
		 updatePayment(payment);
		 
		 
	}

	@Override
	public String handlePayResp(HttpServletRequest req) {
		 List<NameValuePair> list = new ArrayList<NameValuePair>();
		 Map<String,String> params = new HashMap<String,String>();
		 
		 String version = req.getParameter("version");
		 params.put("version", version);
		 list.add(new BasicNameValuePair("version", this.version));
		
		 String charset = req.getParameter("charset");
		 params.put("charset", charset);
		 list.add(new BasicNameValuePair("charset", charset));
		 
		 String transType = req.getParameter("transType");
		 params.put("transType", transType);
		 list.add(new BasicNameValuePair("transType", transType));
		 
		 String merId = req.getParameter("merId");
		 params.put("merId", merId);
		 list.add(new BasicNameValuePair("merId", merId));
		 
		 String transStatus = req.getParameter("transStatus");
		 params.put("transStatus", transStatus);
		 list.add(new BasicNameValuePair("transStatus",transStatus));
		 
		 String respCode = req.getParameter("respCode");
		 params.put("respCode", respCode);
		 list.add(new BasicNameValuePair("respCode", respCode));
		 
		 String respMsg = req.getParameter("respMsg");
		 params.put("respMsg", respMsg);
		 list.add(new BasicNameValuePair("respMsg", respMsg));
		 
		 String qn = req.getParameter("qn");
		 params.put("qn", qn);
		 list.add(new BasicNameValuePair("qn",qn));
		 
		 String orderNumber = req.getParameter("orderNumber");
		 params.put("orderNumber", orderNumber);
		 list.add(new BasicNameValuePair("orderNumber", orderNumber));
		 
		 String orderTime = req.getParameter("orderTime");
		 params.put("orderTime", orderTime);
		 list.add(new BasicNameValuePair("orderTime", orderTime));
		 
		 String settleAmount = req.getParameter("settleAmount");
		 params.put("settleAmount", settleAmount);
		 list.add(new BasicNameValuePair("settleAmount", settleAmount));
		 
		 String settleCurrency = req.getParameter("settleCurrency");
		 params.put("settleCurrency", settleCurrency);
		 list.add(new BasicNameValuePair("settleCurrency", settleCurrency));
		 
		 String settleDate = req.getParameter("settleDate");
		 params.put("settleDate", settleDate);
		 list.add(new BasicNameValuePair("settleDate", settleDate));
		 
		 String exchangeDate = req.getParameter("exchangeDate");
		 params.put("exchangeDate", exchangeDate);
		 list.add(new BasicNameValuePair("exchangeDate", exchangeDate));
		 
		 String exchangeRate = req.getParameter("exchangeRate");
		 params.put("exchangeRate", exchangeRate);
		 list.add(new BasicNameValuePair("exchangeRate", exchangeRate));
		 
		 String merReserved = req.getParameter("merReserved");
		 params.put("merReserved", merReserved);
		 list.add(new BasicNameValuePair("merReserved", merReserved));
		 
		 String reqReserved = req.getParameter("reqReserved");
		 params.put("reqReserved", reqReserved);
		 list.add(new BasicNameValuePair("reqReserved", reqReserved));
		 
		 String sysReserved = req.getParameter("sysReserved");
		 params.put("sysReserved", sysReserved);
		 list.add(new BasicNameValuePair("sysReserved",sysReserved));
		 
		 String newSignature = this.getSignature(list);
		 
		 String signMethod = req.getParameter("signMethod");
		 params.put("signMethod", signMethod);
		 
		 String signature = req.getParameter("signature");
		 params.put("signature", signature);
		 
		 if(!newSignature.equals(signature)) {
			logger.error("Check Signature not pass: ");
			logger.error(params.get("signature"));
			return "fail";
		 }
		 Order order = this.orderDao.getOrder(orderNumber);
		 if(order == null) {
			logger.error("Order not fount for payment notify: ");
			logger.error("orderNum="+orderNumber);
			throw new CommonException("OrderNotFound",orderNumber);
		 }
		 order.setStatus(Order.Status.PAYMENTED.name());
		 this.orderDao.update(order);
		 
		 Payment payment = this.payDao.getPaymentByOrderNum(orderNumber);
		 if(payment == null) {
			logger.error("Order not fount for payment notify: ");
			logger.error("orderNum="+orderNumber);
			throw new CommonException("PaymentNotFound",orderNumber);
		 }
		 payment.setStatus(Payment.Status.FINISHED.name());
		 this.payDao.update(payment);
		 
		return "success";
	}

	
	
	private boolean checkResp(String merId2, String orderNum, String string,
			String currency, String orderTime, String transType,
			String string2, String string3) {
		return pubLink.verifyTransResponse(merId2, orderNum, string,currency, 
				orderTime, transType,string2, string3);
	}
	
	private Map<String,String> getMapParams(String content) {
		Map<String,String> params = new HashMap<String,String>();
		String[] ps = content.split("&");
		for(String ks : ps) {
			String [] keyvalues = ks.split("=");
			params.put(keyvalues[0], keyvalues[1]);
		}
		return params;
	}

	@Override
	public Payment payOrder(Order order) {
		if(isInit) {
			init();
		}
		if(order.getAmount() <= 0) {
			logger.error(" Pay Amount Not more than 0: " + order.toString());
			throw new CommonException("MoneyNoMoreThanZero",order.getAmount().toString());
		}
		
		Payment payment = new Payment();		
		payment.setAmount(order.getAmount());
		payment.setCurrency(Payment.Currency.RMB.getValue());
		payment.setId(generator.getStringId(Payment.class));
		payment.setMobile(order.getMobile());
		Calendar dar = Calendar.getInstance();
		dar.setTime(new Date());
		payment.setOrderTime(dar.getTime());
		dar.add(Calendar.DAY_OF_MONTH, 1);
		payment.setOrderTimeout(dar.getTime());
		payment.setRemark(order.getRemark());
		payment.setStatus(Payment.Status.WAIN_FOR_RESULT.name());
		payment.setTypecode(Typecode.COMMON.name());
		payment.setTransType(Payment.UnionTransType.Comsume.getValue());
		payment.setOrder(order);
		this.savePayment(payment);
		
		String orderTime = DateUtils.formatDate(payment.getOrderTime(), UNION_DATETIME_FORMAT);
		String orderTimeout = DateUtils.formatDate(payment.getOrderTimeout(), UNION_DATETIME_FORMAT);
		//把订单数据推给银联(订单推送接口),根据返回结果是否为true
		// 实例化HTTP方法  
        HttpPost request = new HttpPost(tradeUrl);  
      /*  String signature = this.priLink.signOrder(this.merId, order.getOrderNum(), 
        		payment.getAmount().toString(), payment.getCurrency(), 
        		orderTime, payment.getTransType());*/
        // 创建名/值组列表  
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        
        parameters.add(new BasicNameValuePair("version", this.version));  
        parameters.add(new BasicNameValuePair("charset", this.charset));
  
        parameters.add(new BasicNameValuePair("transType", payment.getTransType()));
        parameters.add(new BasicNameValuePair("merId", this.merId));
        parameters.add(new BasicNameValuePair("backEndUrl", this.backendUrl));
        parameters.add(new BasicNameValuePair("frontEndUrl", this.frontendUrl));
       
        parameters.add(new BasicNameValuePair("orderTime", orderTime));
        
        parameters.add(new BasicNameValuePair("orderTimeout", orderTimeout));
        
        parameters.add(new BasicNameValuePair("orderNumber", order.getOrderNum()));
        String am = ((long)order.getAmount().floatValue()*100)+"";
        parameters.add(new BasicNameValuePair("orderAmount", am));
        parameters.add(new BasicNameValuePair("orderCurrency", payment.getCurrency()));       
        parameters.add(new BasicNameValuePair("orderDescription", order.getRemark()));
        //parameters.add(new BasicNameValuePair("merReserved", ""));
       
        String signature = getSignature(parameters);
        
        parameters.add(new BasicNameValuePair("signMethod", this.signMehtod));
        parameters.add(new BasicNameValuePair("signature", signature));
        // 创建UrlEncodedFormEntity对象  
		try {
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters);  
			request.setEntity(formEntiry);  
			// 执行请求  
			HttpResponse response = httpClient.execute(request);  
			String content = EntityUtils.toString(response.getEntity(), this.charset);
			if(content == null || "".equals(content.trim())) {
				throw new CommonException("PaymentServerError",order.getOrderNum());
			}
			
			Map<String,String> params = this.getMapParams(content);
			
			if(checkResp(this.merId, order.getOrderNum(),
					payment.getAmount().toString(), payment.getCurrency(),
					orderTime, payment.getTransType(), params.get("respCode"), 
					params.get("signature"))) {
				logger.error("Check Signature not pass: ");
				logger.error(params.get("signature"));
				logger.error(payment.toString());
				return payment;			
			}
			
			String respCode = params.get("respCode");
			if("03".equals(respCode)) {
				logger.error("GET erro statu code: " + 03);
				logger.error("resp message: " + params.get("respMsg"));
				logger.error(order.toString());
				logger.error(payment.toString());
				payment.setStatus(Payment.Status.FAIL.name());
				this.updatePayment(payment);
				throw new CommonException("PaymentError",params.get("respMsg"));
			}else if("00".equals(respCode)) {
				logger.info("Get OK Code: " + 00);
				//logger.info(order.toString());
				logger.info(payment.toString());
				String tn = params.get("tn");
				payment.setStatus(Payment.Status.GET_RESULT.name());
				payment.setPayId(tn);			
				this.updatePayment(payment);
			}else if("01".equals(respCode)) {
				logger.info(payment.toString());
			}else {
				logger.error("GET erro statu code: " + respCode);
				logger.error("resp message: " + params.get("respMsg"));
				logger.error(order.toString());
				logger.error(payment.toString());
			}
		} catch (Throwable e) {			
			logger.error(order.toString());
			logger.error(payment.toString());
			logger.error("fail to pay:",e);
			throw new CommonException("ConnectionUnionServerError",order.getOrderNum());
		}
		return payment;
	}


	private Comparator<NameValuePair> compator = new Comparator<NameValuePair>(){
		@Override
		public int compare(NameValuePair arg0, NameValuePair arg1) {				
			return arg0.getName().compareTo(arg1.getName());
		}        	
    };
    
	private String getSignature(List<NameValuePair> parameters) {
		
		List<NameValuePair> ps = new ArrayList<NameValuePair>();
		for(NameValuePair nvp : parameters) {
			if(!(nvp.getValue() == null && nvp.getValue().trim().equals(""))) {
				ps.add(nvp);
			}
		}	
		parameters.clear();
		parameters.addAll(ps);		
		Collections.sort(parameters, compator);
		
		StringBuffer sb = new StringBuffer();
		try {
			for(NameValuePair nvp : parameters) {
				sb.append("&").append(nvp.getName()).append("=")
				.append(URLEncoder.encode(nvp.getValue(), this.charset));
			}
		} catch (UnsupportedEncodingException e) {
			this.logger.error(e);
			throw new CommonException("PaymentError");
		}
		StringBuffer s = sb.replace(0, 1, "&");
		return priLink.Sign(s.toString());
		//return s.toString();
	}
	
}
